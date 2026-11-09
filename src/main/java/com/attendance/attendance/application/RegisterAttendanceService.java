package com.attendance.attendance.application;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.attendance.attendance.api.RegisterAttendanceUseCase;
import com.attendance.attendance.api.RegistrationResult;
import com.attendance.attendance.application.port.out.AttendanceRepository;
import com.attendance.attendance.domain.AttendanceRecord;
import com.attendance.enrollment.api.CheckEnrollmentUseCase;
import com.attendance.session.api.ValidateTokenUseCase;
import com.attendance.session.api.ValidatedSession;

/**
 * <h2>Propósito</h2>
 * Implementa {@code RegisterAttendanceUseCase}: ValidateToken → CheckEnrollment → save.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: orquesta; cada verificación vive en el módulo dueño.<br>
 * D: depende de las api de session y enrollment, no de sus clases internas.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: token crudo, correo del participante.</li>
 *   <li>Salida: {@code RegistrationResult}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Service}. Constructor con {@code ValidateTokenUseCase}, {@code CheckEnrollmentUseCase}, {@code AttendanceRepository}, {@code Clock}.</li>
 *   <li>{@code validate(token)} vacío → {@code InvalidToken}.</li>
 *   <li>{@code !validated.open()} → {@code SessionClosed}.</li>
 *   <li>{@code findParticipantId(email, courseId)} vacío → {@code NotEnrolled}.</li>
 *   <li>{@code exists(...)} → {@code AlreadyRegistered}.</li>
 *   <li>{@code save(new AttendanceRecord(..., clock.instant()))}; si devuelve false (carrera) → {@code AlreadyRegistered}; si no → {@code Registered}.</li>
 *   <li>Cada paso es un retorno temprano: sin {@code if} anidados.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code session.api.ValidateTokenUseCase}</li>
 *   <li>{@code enrollment.api.CheckEnrollmentUseCase}</li>
 *   <li>{@code AttendanceRepository} (port.out)</li>
 *   <li>{@code Clock}</li>
 * </ul>
 */
// Sin @Transactional a propósito: un choque con la PK marcaría toda la transacción para rollback.
@Service
public class RegisterAttendanceService implements RegisterAttendanceUseCase {

    private final ValidateTokenUseCase validateToken;
    private final CheckEnrollmentUseCase checkEnrollment;
    private final AttendanceRepository attendanceRepository;
    private final Clock clock;

    public RegisterAttendanceService(ValidateTokenUseCase validateToken, CheckEnrollmentUseCase checkEnrollment,
            AttendanceRepository attendanceRepository, Clock clock) {
        this.validateToken = validateToken;
        this.checkEnrollment = checkEnrollment;
        this.attendanceRepository = attendanceRepository;
        this.clock = clock;
    }

    @Override
    public RegistrationResult register(String rawToken, String participantEmail) {
        final Optional<ValidatedSession> validated = validateToken.validate(rawToken);
        if (validated.isEmpty()) {
            return new RegistrationResult.InvalidToken();
        }
        if (!validated.get().open()) {
            return new RegistrationResult.SessionClosed();
        }
        final long sessionId = validated.get().sessionId();
        final Optional<Long> participantId =
                checkEnrollment.findParticipantId(participantEmail, validated.get().courseId());
        if (participantId.isEmpty()) {
            return new RegistrationResult.NotEnrolled();
        }
        if (attendanceRepository.exists(sessionId, participantId.get())) {
            return new RegistrationResult.AlreadyRegistered();
        }
        return save(sessionId, participantId.get());
    }

    private RegistrationResult save(long sessionId, long participantId) {
        final Instant now = clock.instant();
        final boolean saved = attendanceRepository.save(new AttendanceRecord(sessionId, participantId, now));
        return saved ? new RegistrationResult.Registered(sessionId, now) : new RegistrationResult.AlreadyRegistered();
    }
}
