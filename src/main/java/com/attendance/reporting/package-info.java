/**
 * <h2>Propósito</h2>
 * Módulo de reportes: exporta la asistencia de una sesión en distintos formatos.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo lectura (vista {@code attendance_report}); no escribe en ninguna tabla.<br>
 * O: nuevo formato = nueva clase {@code ReportExporter} + valor en {@code ReportFormat}.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Expone (api): {@code GenerateReportUseCase}, {@code ReportFormat}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Implementar {@code ReportRow} → {@code CsvReportExporter} → {@code GenerateReportService} → adaptador → controlador.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code shared}, {@code identity.api}.</li>
 * </ul>
 *
 * <h2>Notas</h2>
 * Lee datos de otros módulos SOLO a través de la vista SQL {@code attendance_report} (contrato de solo lectura).<br>
 */
@org.springframework.modulith.ApplicationModule(displayName = "Reporting")
package com.attendance.reporting;
