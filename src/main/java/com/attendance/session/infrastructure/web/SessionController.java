package com.attendance.session.infrastructure.web;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.attendance.identity.api.CurrentUser;
import com.attendance.identity.api.GetCurrentUserUseCase;
import com.attendance.session.api.CloseSessionUseCase;
import com.attendance.session.api.CurrentToken;
import com.attendance.session.api.GetCurrentTokenUseCase;
import com.attendance.session.api.StartSessionResult;
import com.attendance.session.api.StartSessionUseCase;
import com.attendance.session.infrastructure.qr.ZxingQrImageRenderer;
import com.attendance.shared.config.AttendanceProperties;

/**
 * <h2>Propósito</h2>
 * Controlador delgado: iniciar sesión, página de proyección, QR actual en PNG y cerrar.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: valida, llama casos de uso y responde.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code POST /instructor/sessions} (courseId) → redirige a la proyección.</li>
 *   <li>{@code GET /instructor/sessions/{id}} → vista {@code instructor/session}.</li>
 *   <li>{@code GET /instructor/sessions/{id}/qr.png} → imagen; cabecera {@code Cache-Control: no-store}.</li>
 *   <li>{@code GET /instructor/sessions/{id}/token-info} → JSON {@code {expiresAt}} para la cuenta regresiva (opcional).</li>
 *   <li>{@code POST /instructor/sessions/{id}/close} → redirige al reporte.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Controller}. Constructor con {@code GetCurrentUserUseCase}, {@code StartSessionUseCase}, {@code GetCurrentTokenUseCase}, {@code CloseSessionUseCase}, {@code ZxingQrImageRenderer}.</li>
 *   <li>Para el PNG: armar la URL absoluta {@code /attend?t=} + token con {@code ServletUriComponentsBuilder} y pasarla al renderer.</li>
 *   <li>Token vacío → 404 (sin detalles).</li>
 *   <li>Pasar {@code windowSeconds} a la vista para que el JS sepa cada cuánto recargar.</li>
 *   <li>// SECURITY: el correo del instructor sale de {@code GetCurrentUserUseCase}, nunca de la URL.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>api de session</li>
 *   <li>{@code identity.api.GetCurrentUserUseCase}</li>
 *   <li>{@code ZxingQrImageRenderer}</li>
 * </ul>
 */
@Controller
public class SessionController {

    private static final String SESSIONS_PATH = "/instructor/sessions";
    private static final String SESSION_PATH = SESSIONS_PATH + "/{id}";
    private static final String SESSION_VIEW = "instructor/session";
    private static final String REDIRECT_TO_SESSIONS = "redirect:" + SESSIONS_PATH + "/";
    private static final String REPORT_SUFFIX = "/report";
    private static final String ATTEND_PATH = "/attend";
    private static final String TOKEN_PARAM = "t";

    private final GetCurrentUserUseCase getCurrentUser;
    private final StartSessionUseCase startSession;
    private final GetCurrentTokenUseCase getCurrentToken;
    private final CloseSessionUseCase closeSession;
    private final ZxingQrImageRenderer qrImageRenderer;
    private final AttendanceProperties properties;

    public SessionController(GetCurrentUserUseCase getCurrentUser, StartSessionUseCase startSession,
            GetCurrentTokenUseCase getCurrentToken, CloseSessionUseCase closeSession,
            ZxingQrImageRenderer qrImageRenderer, AttendanceProperties properties) {
        this.getCurrentUser = getCurrentUser;
        this.startSession = startSession;
        this.getCurrentToken = getCurrentToken;
        this.closeSession = closeSession;
        this.qrImageRenderer = qrImageRenderer;
        this.properties = properties;
    }

    @PostMapping(SESSIONS_PATH)
    public String start(@RequestParam long courseId) {
        final StartSessionResult result = startSession.start(courseId, instructorEmail());
        return switch (result) {
            case StartSessionResult.Started started -> REDIRECT_TO_SESSIONS + started.sessionId();
            case StartSessionResult.AlreadyOpen open -> REDIRECT_TO_SESSIONS + open.sessionId();
            case StartSessionResult.NotCourseOwner notOwner -> throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        };
    }

    @GetMapping(SESSION_PATH)
    public String projection(@PathVariable long id, Model model) {
        requireToken(id);
        model.addAttribute("sessionId", id);
        model.addAttribute("windowSeconds", properties.qr().windowSeconds());
        return SESSION_VIEW;
    }

    @GetMapping(value = SESSION_PATH + "/qr.png", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> qr(@PathVariable long id) {
        final String url = ServletUriComponentsBuilder.fromCurrentContextPath().path(ATTEND_PATH)
                .queryParam(TOKEN_PARAM, requireToken(id).value()).toUriString();
        return ResponseEntity.ok().cacheControl(CacheControl.noStore())
                .body(qrImageRenderer.render(url, ZxingQrImageRenderer.DEFAULT_SIZE));
    }

    @GetMapping(SESSION_PATH + "/token-info")
    @ResponseBody
    public ResponseEntity<Map<String, Instant>> tokenInfo(@PathVariable long id) {
        return ResponseEntity.ok().cacheControl(CacheControl.noStore())
                .body(Map.of("expiresAt", requireToken(id).expiresAt()));
    }

    @PostMapping(SESSION_PATH + "/close")
    public String close(@PathVariable long id) {
        if (!closeSession.close(id, instructorEmail())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return REDIRECT_TO_SESSIONS + id + REPORT_SUFFIX;
    }

    private CurrentToken requireToken(long sessionId) {
        return getCurrentToken.currentToken(sessionId, instructorEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // SECURITY: el correo del instructor sale del usuario autenticado, nunca de la URL.
    private String instructorEmail() {
        return getCurrentUser.currentUser().map(CurrentUser::email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }
}
