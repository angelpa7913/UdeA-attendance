package com.attendance.reporting.application;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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

    @Test
    @Disabled("TODO")
    void usesExporterThatSupportsFormat() {
        // Con dos exportadores, solo se llama al que soporta el formato.
    }

    @Test
    @Disabled("TODO")
    void returnsEmptyWhenNoExporterSupportsFormat() {
        // Ningún exportador soporta el formato → vacío.
    }

    @Test
    @Disabled("TODO")
    void returnsEmptyWhenSessionNotOwned() {
        // ReportDataSource sin filas para ese instructor → vacío.
    }

    @Test
    @Disabled("TODO")
    void buildsFileNameWithDateFromClock() {
        // El nombre del archivo usa la fecha de Clock.fixed.
    }
}
