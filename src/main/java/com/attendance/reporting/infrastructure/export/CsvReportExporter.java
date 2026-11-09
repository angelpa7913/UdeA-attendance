package com.attendance.reporting.infrastructure.export;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.attendance.reporting.api.ReportFormat;
import com.attendance.reporting.application.port.out.ReportExporter;
import com.attendance.reporting.application.port.out.ReportRow;

/**
 * <h2>Propósito</h2>
 * Implementa {@code ReportExporter} para CSV.
 *
 * <h2>Responsabilidad SOLID</h2>
 * O: un formato = una clase. S: solo escribir CSV.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code supports(CSV)} → true.</li>
 *   <li>{@code export(rows)} → bytes UTF-8.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Component}. Separador y encabezados como constantes.</li>
 *   <li>Escribir encabezado y luego cada fila (stream o {@code for} sobre la lista).</li>
 *   <li>Escapar valores con comillas, comas o saltos de línea.</li>
 *   <li>// SECURITY: evitar inyección de fórmulas en Excel: si un valor empieza con {@code = + - @}, anteponer {@code '}.</li>
 *   <li>Opcional: BOM UTF-8 para que Excel muestre bien tildes.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code ReportExporter}, {@code ReportRow} (port.out)</li>
 *   <li>{@code ReportFormat}</li>
 * </ul>
 */
@Component
public class CsvReportExporter implements ReportExporter {

    private static final String SEPARATOR = ",";
    private static final String LINE_END = "\r\n";
    private static final String QUOTE = "\"";
    private static final String BYTE_ORDER_MARK = "\uFEFF";
    private static final String FORMULA_GUARD = "'";
    private static final String FORMULA_PREFIXES = "=+-@\t\r";
    private static final String CHARS_TO_QUOTE = ",\"\r\n";
    private static final String HEADER = String.join(SEPARATOR, "course_code", "course_group", "course_name",
            "session_id", "session_opened_at", "participant_email", "participant_name", "external_id",
            "registered_at");

    @Override
    public boolean supports(ReportFormat format) {
        return format == ReportFormat.CSV;
    }

    @Override
    public byte[] export(List<ReportRow> rows) {
        final String body = rows.stream().map(CsvReportExporter::toLine).collect(Collectors.joining(LINE_END));
        return (BYTE_ORDER_MARK + HEADER + LINE_END + body + LINE_END).getBytes(StandardCharsets.UTF_8);
    }

    private static String toLine(ReportRow row) {
        return Stream.of(row.courseCode(), row.courseGroup(), row.courseName(), row.sessionId(),
                        row.sessionOpenedAt(), row.participantEmail(), row.participantName(), row.externalId(),
                        row.registeredAt())
                .map(value -> escape(Objects.toString(value, "")))
                .collect(Collectors.joining(SEPARATOR));
    }

    private static String escape(String value) {
        final String guarded = guardFormula(value);
        final boolean mustQuote = guarded.chars().anyMatch(c -> CHARS_TO_QUOTE.indexOf(c) >= 0);
        return mustQuote ? QUOTE + guarded.replace(QUOTE, QUOTE + QUOTE) + QUOTE : guarded;
    }

    // SECURITY: evita que Excel interprete como fórmula un valor escrito por un usuario.
    private static String guardFormula(String value) {
        final boolean isFormula = !value.isEmpty() && FORMULA_PREFIXES.indexOf(value.charAt(0)) >= 0;
        return isFormula ? FORMULA_GUARD + value : value;
    }
}
