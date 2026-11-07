package com.attendance.session.application;

/**
 * <h2>Propósito</h2>
 * Implementa {@code GetCurrentTokenUseCase}: calcula el token de la ventana actual.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: un caso de uso. Sin hilos ni bucles: cada petición calcula el token del momento.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: sessionId, instructorEmail.</li>
 *   <li>Salida: {@code Optional<CurrentToken>}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Service}. Constructor con {@code SessionRepository}, {@code TokenSigner}, {@code AttendanceProperties}, {@code Clock}.</li>
 *   <li>Buscar la sesión; si no existe, está cerrada o no es del instructor → vacío.</li>
 *   <li>{@code window = TimeWindow.of(clock.instant(), windowSeconds)}.</li>
 *   <li>{@code signature = signer.sign(sessionId + "." + window, secret)}; armar {@code QrToken} y serializar.</li>
 *   <li>{@code expiresAt = window.end(windowSeconds)} para la cuenta regresiva del navegador.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code SessionRepository}, {@code TokenSigner} (port.out)</li>
 *   <li>{@code AttendanceProperties}</li>
 *   <li>{@code Clock}</li>
 * </ul>
 */
public class GetCurrentTokenService {
}
