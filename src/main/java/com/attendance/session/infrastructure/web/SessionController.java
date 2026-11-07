package com.attendance.session.infrastructure.web;

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
public class SessionController {
}
