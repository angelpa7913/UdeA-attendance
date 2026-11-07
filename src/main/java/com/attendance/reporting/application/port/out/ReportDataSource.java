package com.attendance.reporting.application.port.out;

/**
 * <h2>Propósito</h2>
 * Puerto de salida: filas de la vista {@code attendance_report} para una sesión.
 *
 * <h2>Responsabilidad SOLID</h2>
 * I/D: una consulta; el servicio no sabe que es SQL.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code List<ReportRow> rowsFor(long sessionId, String instructorEmail)} (vacía si la sesión no es del instructor).</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar el método.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code ReportRow}</li>
 * </ul>
 */
public interface ReportDataSource {
}
