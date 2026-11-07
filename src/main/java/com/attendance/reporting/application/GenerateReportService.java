package com.attendance.reporting.application;

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
public class GenerateReportService {
}
