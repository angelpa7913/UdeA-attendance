/**
 * <h2>Propósito</h2>
 * Módulo de sesiones: abrir y cerrar una sesión de clase y emitir/validar el token QR rotativo.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: dueño de la tabla {@code sessions} y del secreto de cada sesión.<br>
 * D: depende de puertos ({@code TokenSigner}, {@code SecretGenerator}, {@code SessionRepository}), no de la criptografía concreta.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Expone (api): {@code StartSessionUseCase}, {@code GetCurrentTokenUseCase}, {@code ValidateTokenUseCase}, {@code CloseSessionUseCase}, {@code ValidatedSession}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Empezar por {@code TimeWindow} y {@code QrToken} (puros y fáciles de probar), luego {@code ClassSession}, luego los servicios.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code shared}, {@code identity.api}, {@code enrollment.api}.</li>
 * </ul>
 *
 * <h2>Notas</h2>
 * La rotación NO usa hilos ni bucles: el token se calcula a partir de la ventana de tiempo actual.<br>
 * No depende de attendance (attendance depende de session); el contador de asistentes lo sirve attendance.<br>
 */
@org.springframework.modulith.ApplicationModule(displayName = "Session")
package com.attendance.session;
