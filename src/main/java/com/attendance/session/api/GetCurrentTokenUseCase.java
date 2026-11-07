package com.attendance.session.api;

/**
 * <h2>Propósito</h2>
 * Puerto de entrada: el token QR vigente de una sesión abierta (lo pide el navegador del instructor cada N segundos).
 *
 * <h2>Responsabilidad SOLID</h2>
 * S/I: una consulta.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: {@code long sessionId}, {@code String instructorEmail}.</li>
 *   <li>Salida: {@code Optional<CurrentToken>} — crea en api {@code record CurrentToken(String value, Instant expiresAt)}; vacío si la sesión no existe, está cerrada o no es del instructor.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Crear {@code CurrentToken} en este paquete.</li>
 *   <li>Declarar {@code Optional<CurrentToken> currentToken(long sessionId, String instructorEmail);}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 */
public interface GetCurrentTokenUseCase {
}
