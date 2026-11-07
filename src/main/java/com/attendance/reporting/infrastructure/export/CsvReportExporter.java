package com.attendance.reporting.infrastructure.export;

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
public class CsvReportExporter {
}
