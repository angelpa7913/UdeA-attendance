package com.attendance.reporting.api;

import java.util.Optional;

/**
 * <h2>Propósito</h2>
 * Puerto de entrada: generar el reporte de una sesión en el formato pedido.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S/I: una operación.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: {@code long sessionId}, {@code ReportFormat format}, {@code String instructorEmail}.</li>
 *   <li>Salida: {@code Optional<GeneratedReport>} — crea en api {@code record GeneratedReport(String fileName, String contentType, byte[] content)}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Crear {@code GeneratedReport} (copia defensiva del arreglo en el constructor y en el accesor).</li>
 *   <li>Declarar {@code Optional<GeneratedReport> generate(long sessionId, ReportFormat format, String instructorEmail);}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code ReportFormat}</li>
 * </ul>
 */
public interface GenerateReportUseCase {

    Optional<GeneratedReport> generate(long sessionId, ReportFormat format, String instructorEmail);
}
