package com.attendance.reporting.application.port.out;

/**
 * <h2>Propósito</h2>
 * Fila neutral del reporte, independiente del formato de salida.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo datos.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Componentes sugeridos: {@code String courseCode}, {@code String courseGroup}, {@code long sessionId}, {@code Instant sessionOpenedAt}, {@code String participantEmail}, {@code String participantName}, {@code String externalId}, {@code Instant registeredAt} (Optional si quieres incluir inscritos ausentes).</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Agregar los componentes; que coincidan con las columnas de la vista.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 *
 * <h2>Notas</h2>
 * Archivo agregado respecto al CONTEXTO: es el tipo que devuelve {@code ReportDataSource} y recibe {@code ReportExporter}.<br>
 */
public record ReportRow() {
}
