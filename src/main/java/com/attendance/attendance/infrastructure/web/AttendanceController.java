package com.attendance.attendance.infrastructure.web;

import java.util.Map;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.attendance.attendance.api.CountAttendeesUseCase;
import com.attendance.attendance.api.RegisterAttendanceUseCase;
import com.attendance.attendance.api.RegistrationResult;
import com.attendance.identity.api.CurrentUser;
import com.attendance.identity.api.GetCurrentUserUseCase;
import com.attendance.session.api.GetCurrentTokenUseCase;

/**
 * <h2>Propósito</h2>
 * Controlador delgado: el participante llega desde el QR y ve el resultado.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: valida, llama el caso de uso y responde.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code GET /attend?t=token} → vista {@code attend/result}.</li>
 *   <li>{@code GET /instructor/sessions/{id}/count} → JSON {@code {count}} para la proyección.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Controller}. Constructor con {@code GetCurrentUserUseCase}, {@code RegisterAttendanceUseCase}, {@code CountAttendeesUseCase}.</li>
 *   <li>Si no hay usuario, Spring Security ya redirigió al login y volverá aquí con el mismo {@code t}.</li>
 *   <li>Llamar {@code register(t, user.email())} y traducir el {@code RegistrationResult} con {@code switch} a un mensaje.</li>
 *   <li>// SECURITY: no mostrar por qué falló un token más allá de "QR inválido o vencido".</li>
 *   <li>// SECURITY: evaluar si debe ser {@code POST} (con confirmación) para que un enlace reenviado no registre a otro sin querer; el token vence en segundos de todas formas.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code identity.api.GetCurrentUserUseCase}</li>
 *   <li>api de attendance</li>
 * </ul>
 */
@Controller
public class AttendanceController {

    private static final String RESULT_VIEW = "attend/result";

    private final GetCurrentUserUseCase getCurrentUser;
    private final RegisterAttendanceUseCase registerAttendance;
    private final CountAttendeesUseCase countAttendees;
    private final GetCurrentTokenUseCase getCurrentToken;

    public AttendanceController(GetCurrentUserUseCase getCurrentUser, RegisterAttendanceUseCase registerAttendance,
            CountAttendeesUseCase countAttendees, GetCurrentTokenUseCase getCurrentToken) {
        this.getCurrentUser = getCurrentUser;
        this.registerAttendance = registerAttendance;
        this.countAttendees = countAttendees;
        this.getCurrentToken = getCurrentToken;
    }

    @GetMapping("/attend")
    public String attend(@RequestParam("t") String token, Model model) {
        final RegistrationResult result = registerAttendance.register(token, currentEmail());
        model.addAttribute("status", statusOf(result));
        if (result instanceof RegistrationResult.Registered registered) {
            model.addAttribute("registeredAt", registered.registeredAt());
        }
        return RESULT_VIEW;
    }

    @GetMapping("/instructor/sessions/{id}/count")
    @ResponseBody
    public ResponseEntity<Map<String, Long>> count(@PathVariable long id) {
        // SECURITY: el token solo existe para el instructor dueño de una sesión abierta.
        if (getCurrentToken.currentToken(id, currentEmail()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().cacheControl(CacheControl.noStore())
                .body(Map.of("count", countAttendees.countBySession(id)));
    }

    // SECURITY: la vista no da más detalle que "QR inválido o vencido".
    private static String statusOf(RegistrationResult result) {
        return switch (result) {
            case RegistrationResult.Registered registered -> "registered";
            case RegistrationResult.InvalidToken invalid -> "invalid";
            case RegistrationResult.SessionClosed closed -> "closed";
            case RegistrationResult.NotEnrolled notEnrolled -> "not-enrolled";
            case RegistrationResult.AlreadyRegistered already -> "already";
        };
    }

    private String currentEmail() {
        return getCurrentUser.currentUser().map(CurrentUser::email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
    }
}
