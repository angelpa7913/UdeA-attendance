package com.attendance.session.application;

/**
 * <h2>Propósito</h2>
 * Implementa {@code CloseSessionUseCase}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: un caso de uso.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: sessionId, instructorEmail.</li>
 *   <li>Salida: {@code boolean}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>{@code @Service}. Constructor con {@code SessionRepository}, {@code Clock}.</li>
 *   <li>Buscar la sesión; si no existe → false.</li>
 *   <li>// SECURITY: si {@code !session.isOwnedBy(instructorEmail)} → false.</li>
 *   <li>{@code save(session.close(clock.instant()))} → true.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code SessionRepository} (port.out)</li>
 *   <li>{@code Clock}</li>
 * </ul>
 */
public class CloseSessionService {
}
