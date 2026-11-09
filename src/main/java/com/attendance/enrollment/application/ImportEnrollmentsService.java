package com.attendance.enrollment.application;

import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attendance.enrollment.api.ImportEnrollmentsUseCase;
import com.attendance.enrollment.api.ImportSummary;
import com.attendance.enrollment.application.port.out.EnrollmentRepository;
import com.attendance.enrollment.application.port.out.EnrollmentRow;
import com.attendance.enrollment.application.port.out.EnrollmentSource;
import com.attendance.enrollment.domain.Course;
import com.attendance.enrollment.domain.Enrollment;
import com.attendance.enrollment.domain.Participant;
import com.attendance.shared.config.AttendanceProperties;

/**
 * <h2>Propósito</h2>
 * Implementa {@code ImportEnrollmentsUseCase}: elige la fuente, valida filas y guarda.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: orquesta la importación. O: recibe {@code List<EnrollmentSource>} y elige con {@code supports} (sin if por formato).<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: sourceType, datos, correo del instructor.</li>
 *   <li>Salida: {@code ImportSummary}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Service}. Constructor con {@code List<EnrollmentSource>}, {@code EnrollmentRepository}, {@code AttendanceProperties}.</li>
 *   <li>Buscar la fuente con {@code stream().filter(s -> s.supports(type)).findFirst()}; si no hay, resumen con error.</li>
 *   <li>Leer filas. Por cada una (bucle {@code for} sobre la lista, con fin claro): validar email y dominio permitido.</li>
 *   <li>// SECURITY: el instructor solo puede importar cursos donde él es el instructor; la fila no decide el instructor.</li>
 *   <li>Guardar participante, curso e inscripción; contar importadas y omitidas.</li>
 *   <li>Marcar el método con {@code @Transactional} (o hacerlo en el adaptador) para que una importación sea todo o nada.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code EnrollmentSource}, {@code EnrollmentRepository} (port.out)</li>
 *   <li>{@code AttendanceProperties} (shared)</li>
 * </ul>
 */
@Service
public class ImportEnrollmentsService implements ImportEnrollmentsUseCase {

    private static final int MAX_EMAIL_LENGTH = 320;
    private static final int MAX_NAME_LENGTH = 200;
    private static final int MAX_EXTERNAL_ID_LENGTH = 100;
    private static final int MAX_CODE_LENGTH = 50;
    private static final char AT = '@';

    private final List<EnrollmentSource> sources;
    private final EnrollmentRepository enrollmentRepository;
    private final Set<String> allowedDomains;

    public ImportEnrollmentsService(List<EnrollmentSource> sources, EnrollmentRepository enrollmentRepository,
            AttendanceProperties properties) {
        this.sources = List.copyOf(sources);
        this.enrollmentRepository = enrollmentRepository;
        this.allowedDomains = Set.copyOf(properties.allowedEmailDomains());
    }

    @Override
    @Transactional
    public ImportSummary importEnrollments(String sourceType, InputStream data, String instructorEmail) {
        return sources.stream()
                .filter(source -> source.supports(sourceType))
                .findFirst()
                .map(source -> importFrom(source, data, instructorEmail))
                .orElseGet(() -> failure("Tipo de fuente no soportado"));
    }

    private ImportSummary importFrom(EnrollmentSource source, InputStream data, String instructorEmail) {
        final List<EnrollmentRow> rows;
        try {
            rows = source.read(data);
        } catch (IllegalArgumentException | UncheckedIOException e) {
            return failure("No se pudo leer el archivo: " + e.getMessage());
        }
        return importRows(rows, instructorEmail);
    }

    private ImportSummary importRows(List<EnrollmentRow> rows, String instructorEmail) {
        final List<String> errors = new ArrayList<>();
        int imported = 0;
        for (int index = 0; index < rows.size(); index++) {
            final RowOutcome outcome = importRow(rows.get(index), instructorEmail);
            imported += outcome.imported() ? 1 : 0;
            final int rowNumber = index + 1;
            outcome.error().ifPresent(error -> errors.add("Fila " + rowNumber + ": " + error));
        }
        return new ImportSummary(imported, rows.size() - imported, errors);
    }

    private RowOutcome importRow(EnrollmentRow row, String instructorEmail) {
        final Optional<String> invalid = validationError(row);
        if (invalid.isPresent()) {
            return new RowOutcome(false, invalid);
        }
        // SECURITY: el instructor es quien importa; la fila nunca lo decide.
        final Course course = enrollmentRepository.saveCourseIfAbsent(
                Course.create(row.courseCode(), row.courseGroup(), row.courseName(), instructorEmail));
        // SECURITY: nadie puede inscribir personas en el curso de otro instructor.
        if (!course.isInstructedBy(instructorEmail)) {
            return new RowOutcome(false, Optional.of("el curso pertenece a otro instructor"));
        }
        final Participant participant = enrollmentRepository.saveParticipantIfAbsent(
                Participant.create(row.participantEmail(), row.participantName(), row.externalId()));
        final boolean enrolled = enrollmentRepository.enroll(
                new Enrollment(participant.id().orElseThrow(), course.id().orElseThrow()));
        return new RowOutcome(enrolled, Optional.empty());
    }

    private Optional<String> validationError(EnrollmentRow row) {
        if (hasBlankRequiredField(row)) {
            return Optional.of("faltan datos obligatorios");
        }
        if (exceedsColumnLimits(row)) {
            return Optional.of("algún valor es demasiado largo");
        }
        if (!isAllowedEmail(row.participantEmail().strip().toLowerCase(Locale.ROOT))) {
            return Optional.of("correo inválido o de un dominio no permitido");
        }
        return Optional.empty();
    }

    private static boolean hasBlankRequiredField(EnrollmentRow row) {
        return Stream.of(row.participantEmail(), row.participantName(), row.courseCode(), row.courseGroup(),
                row.courseName()).anyMatch(value -> value == null || value.isBlank());
    }

    private static boolean exceedsColumnLimits(EnrollmentRow row) {
        return row.participantEmail().strip().length() > MAX_EMAIL_LENGTH
                || row.participantName().strip().length() > MAX_NAME_LENGTH
                || row.courseName().strip().length() > MAX_NAME_LENGTH
                || row.courseCode().strip().length() > MAX_CODE_LENGTH
                || row.courseGroup().strip().length() > MAX_CODE_LENGTH
                || row.externalId() != null && row.externalId().strip().length() > MAX_EXTERNAL_ID_LENGTH;
    }

    private boolean isAllowedEmail(String email) {
        final int at = email.indexOf(AT);
        return at > 0 && at == email.lastIndexOf(AT) && allowedDomains.contains(email.substring(at + 1));
    }

    private static ImportSummary failure(String error) {
        return new ImportSummary(0, 0, List.of(error));
    }

    private record RowOutcome(boolean imported, Optional<String> error) {
    }
}
