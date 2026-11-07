package com.attendance.session.api;

/**
 * <h2>Propósito</h2>
 * Puerto de entrada: ¿este token escaneado es auténtico y vigente?
 *
 * <h2>Responsabilidad SOLID</h2>
 * S/I: una pregunta. Lo usa attendance.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: {@code String rawToken}.</li>
 *   <li>Salida: {@code Optional<ValidatedSession>}. Vacío = token mal formado, firma inválida o ventana vencida.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar {@code Optional<ValidatedSession> validate(String rawToken);}.</li>
 *   <li>Documentar que una sesión cerrada se devuelve con {@code open = false} (así attendance distingue {@code SessionClosed} de {@code InvalidToken}).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code ValidatedSession}</li>
 * </ul>
 */
public interface ValidateTokenUseCase {
}
