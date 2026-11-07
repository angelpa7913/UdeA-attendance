package com.attendance.reporting.application.port.out;

/**
 * <h2>Propósito</h2>
 * Puerto de salida: convierte filas a bytes en un formato.
 *
 * <h2>Responsabilidad SOLID</h2>
 * O: cada formato es una implementación. I: dos métodos.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code boolean supports(ReportFormat format)}</li>
 *   <li>{@code byte[] export(List<ReportRow> rows)}</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar ambos métodos.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code ReportFormat}, {@code ReportRow}</li>
 * </ul>
 */
public interface ReportExporter {
}
