package com.attendance.reporting.infrastructure.web;

/**
 * <h2>Propósito</h2>
 * Controlador delgado: página del reporte y descarga.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: valida, llama el caso de uso y responde.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code GET /instructor/sessions/{id}/report} → vista {@code instructor/report}.</li>
 *   <li>{@code GET /instructor/sessions/{id}/report/download?format=CSV} → archivo.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Controller}. Constructor con {@code GetCurrentUserUseCase}, {@code GenerateReportUseCase}.</li>
 *   <li>Convertir {@code format} a {@code ReportFormat} (Spring lo hace solo; un valor inválido da 400).</li>
 *   <li>Reporte vacío → 404.</li>
 *   <li>Responder {@code ResponseEntity<byte[]>} con {@code Content-Disposition: attachment}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code identity.api.GetCurrentUserUseCase}</li>
 *   <li>api de reporting</li>
 * </ul>
 */
public class ReportController {
}
