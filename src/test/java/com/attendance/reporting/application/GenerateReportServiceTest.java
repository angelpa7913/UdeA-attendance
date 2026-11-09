package com.attendance.reporting.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.attendance.reporting.api.GeneratedReport;
import com.attendance.reporting.api.ReportFormat;
import com.attendance.reporting.application.port.out.ReportDataSource;
import com.attendance.reporting.application.port.out.ReportExporter;
import com.attendance.reporting.application.port.out.ReportRow;
import com.attendance.shared.config.AttendanceProperties;

/**
 * <h2>Propósito</h2>
 * Escenarios de {@code GenerateReportService}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * Prueba unitaria: se ejecuta sin levantar Spring (Mockito o adaptadores en memoria).<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: escenarios descritos en cada método.</li>
 *   <li>Salida: aserciones sobre el resultado.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Crear los dobles de prueba (mocks o fakes en memoria) de cada puerto.</li>
 *   <li>Usar {@code Clock.fixed(...)} cuando el tiempo importe.</li>
 *   <li>Implementar cada escenario y quitar {@code @Disabled}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code GenerateReportService}</li>
 *   <li>Fakes de {@code ReportExporter} y {@code ReportDataSource}</li>
 *   <li>{@code Clock.fixed}</li>
 * </ul>
 */
class GenerateReportServiceTest {

    private static final Instant NOW = Instant.parse("2026-03-04T12:00:00Z");
    private static final String OWNER = "instructor@x.com";
    private static final ReportRow ROW = new ReportRow("C1", "01", "Curso", 1L, NOW, "ana@x.com", "Ana", null, NOW);
    private static final ReportDataSource DATA_SOURCE =
            (sessionId, email) -> OWNER.equals(email) ? List.of(ROW) : List.of();

    @Test
    void usesExporterThatSupportsFormat() {
        final GenerateReportService service = service(List.of(exporter(false, "otro"), exporter(true, "correcto")));

        assertThat(service.generate(1L, ReportFormat.CSV, OWNER))
                .map(report -> new String(report.content(), StandardCharsets.UTF_8))
                .contains("correcto");
    }

    @Test
    void returnsEmptyWhenNoExporterSupportsFormat() {
        assertThat(service(List.of(exporter(false, "otro"))).generate(1L, ReportFormat.CSV, OWNER)).isEmpty();
    }

    @Test
    void returnsEmptyWhenSessionNotOwned() {
        assertThat(service(List.of(exporter(true, "x"))).generate(1L, ReportFormat.CSV, "otro@x.com")).isEmpty();
    }

    @Test
    void buildsFileNameWithDateFromClock() {
        final Optional<GeneratedReport> report =
                service(List.of(exporter(true, "x"))).generate(1L, ReportFormat.CSV, OWNER);

        assertThat(report).map(GeneratedReport::fileName).contains("Institucion-de-prueba-1-2026-03-04.csv");
    }

    private static GenerateReportService service(List<ReportExporter> exporters) {
        final AttendanceProperties properties = new AttendanceProperties("Institución de prueba", List.of("x.com"),
                new AttendanceProperties.Qr(20, 1));
        return new GenerateReportService(exporters, DATA_SOURCE, properties, Clock.fixed(NOW, ZoneOffset.UTC));
    }

    private static ReportExporter exporter(boolean supported, String content) {
        return new ReportExporter() {
            @Override
            public boolean supports(ReportFormat format) {
                return supported;
            }

            @Override
            public byte[] export(List<ReportRow> rows) {
                return content.getBytes(StandardCharsets.UTF_8);
            }
        };
    }
}
