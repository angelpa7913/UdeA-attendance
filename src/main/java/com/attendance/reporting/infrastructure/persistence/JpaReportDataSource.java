package com.attendance.reporting.infrastructure.persistence;

/**
 * <h2>Propósito</h2>
 * Implementa {@code ReportDataSource} leyendo la vista {@code attendance_report}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * D/L: adaptador intercambiable. Solo lectura.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Las del puerto.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Component}. Constructor con {@code JdbcClient} (o {@code EntityManager} con consulta nativa).</li>
 *   <li>SQL estándar: {@code SELECT ... FROM attendance_report WHERE session_id = ? AND instructor_email = ? ORDER BY participant_name}.</li>
 *   <li>// SECURITY: parámetros enlazados, nunca concatenar valores en el SQL.</li>
 *   <li>Mapear cada fila a {@code ReportRow}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code ReportDataSource}, {@code ReportRow} (port.out)</li>
 * </ul>
 */
public class JpaReportDataSource {
}
