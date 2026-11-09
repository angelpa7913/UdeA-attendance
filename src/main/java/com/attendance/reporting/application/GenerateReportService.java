package com.attendance.reporting.application;

import java.text.Normalizer;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.attendance.reporting.api.GenerateReportUseCase;
import com.attendance.reporting.api.GeneratedReport;
import com.attendance.reporting.api.ReportFormat;
import com.attendance.reporting.application.port.out.ReportDataSource;
import com.attendance.reporting.application.port.out.ReportExporter;
import com.attendance.reporting.application.port.out.ReportRow;
import com.attendance.shared.config.AttendanceProperties;

/**
 * <h2>Propósito</h2>
 * Implementa {@code GenerateReportUseCase}: elige el exportador según el formato.
 *
 * <h2>Responsabilidad SOLID</h2>
 * O: recibe {@code List<ReportExporter>}; agregar PDF no toca esta clase.<br>
 * S: no sabe cómo se escribe un CSV.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: sessionId, formato, instructor.</li>
 *   <li>Salida: {@code Optional<GeneratedReport>}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Service}. Constructor con {@code List<ReportExporter>}, {@code ReportDataSource}, {@code AttendanceProperties} (nombre de institución para el archivo) y {@code Clock} (fecha en el nombre).</li>
 *   <li>Buscar exportador con {@code supports(format)}; si no hay → vacío.</li>
 *   <li>// SECURITY: obtener filas filtrando por instructor; si la sesión no es suya → vacío.</li>
 *   <li>Exportar y armar {@code GeneratedReport} con nombre de archivo sin caracteres peligrosos.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code ReportExporter}, {@code ReportDataSource} (port.out)</li>
 *   <li>{@code AttendanceProperties}</li>
 *   <li>{@code Clock}</li>
 * </ul>
 */
@Service
public class GenerateReportService implements GenerateReportUseCase {

    private static final String DIACRITICS = "\\p{M}";
    private static final String UNSAFE_FILE_NAME_CHARS = "[^A-Za-z0-9_-]+";
    private static final String FILE_NAME_SEPARATOR = "-";

    private final List<ReportExporter> exporters;
    private final ReportDataSource reportDataSource;
    private final AttendanceProperties properties;
    private final Clock clock;

    public GenerateReportService(List<ReportExporter> exporters, ReportDataSource reportDataSource,
            AttendanceProperties properties, Clock clock) {
        this.exporters = List.copyOf(exporters);
        this.reportDataSource = reportDataSource;
        this.properties = properties;
        this.clock = clock;
    }

    @Override
    public Optional<GeneratedReport> generate(long sessionId, ReportFormat format, String instructorEmail) {
        return exporters.stream()
                .filter(exporter -> exporter.supports(format))
                .findFirst()
                .flatMap(exporter -> export(exporter, sessionId, format, instructorEmail));
    }

    private Optional<GeneratedReport> export(ReportExporter exporter, long sessionId, ReportFormat format,
            String instructorEmail) {
        // SECURITY: las filas se filtran por instructor; una sesión ajena no devuelve nada.
        final List<ReportRow> rows = reportDataSource.rowsFor(sessionId, instructorEmail);
        if (rows.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new GeneratedReport(fileName(sessionId, format), format.contentType(),
                exporter.export(rows)));
    }

    private String fileName(long sessionId, ReportFormat format) {
        return safe(properties.institutionName()) + FILE_NAME_SEPARATOR + sessionId + FILE_NAME_SEPARATOR
                + LocalDate.now(clock) + "." + format.extension();
    }

    // SECURITY: el nombre del archivo no debe llevar caracteres que alteren la cabecera o la ruta.
    private static String safe(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll(DIACRITICS, "")
                .replaceAll(UNSAFE_FILE_NAME_CHARS, FILE_NAME_SEPARATOR);
    }
}
