package com.attendance.reporting.infrastructure.web;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.attendance.identity.api.CurrentUser;
import com.attendance.identity.api.GetCurrentUserUseCase;
import com.attendance.reporting.api.GenerateReportUseCase;
import com.attendance.reporting.api.GeneratedReport;
import com.attendance.reporting.api.ReportFormat;

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
@Controller
public class ReportController {

    private static final String REPORT_PATH = "/instructor/sessions/{id}/report";
    private static final String REPORT_VIEW = "instructor/report";

    private final GetCurrentUserUseCase getCurrentUser;
    private final GenerateReportUseCase generateReport;

    public ReportController(GetCurrentUserUseCase getCurrentUser, GenerateReportUseCase generateReport) {
        this.getCurrentUser = getCurrentUser;
        this.generateReport = generateReport;
    }

    // La página solo tiene enlaces: los datos se autorizan al descargar.
    @GetMapping(REPORT_PATH)
    public String report(@PathVariable long id, Model model) {
        model.addAttribute("sessionId", id);
        model.addAttribute("formats", ReportFormat.values());
        return REPORT_VIEW;
    }

    @GetMapping(REPORT_PATH + "/download")
    public ResponseEntity<byte[]> download(@PathVariable long id, @RequestParam ReportFormat format) {
        final GeneratedReport report = generateReport.generate(id, format, currentEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(report.contentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment().filename(report.fileName()).build().toString())
                .body(report.content());
    }

    // SECURITY: el correo del instructor sale del usuario autenticado, nunca de la URL.
    private String currentEmail() {
        return getCurrentUser.currentUser().map(CurrentUser::email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }
}
