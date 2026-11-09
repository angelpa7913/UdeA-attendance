package com.attendance.session.api;

/**
 * <h2>Propósito</h2>
 * Puerto de entrada: el instructor cierra su sesión; desde ese momento no se aceptan registros.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S/I: una operación.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: {@code long sessionId}, {@code String instructorEmail}.</li>
 *   <li>Salida: {@code boolean} (true si se cerró) o un resultado tipado si quieres distinguir causas.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar {@code boolean close(long sessionId, String instructorEmail);}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 */
public interface CloseSessionUseCase {

    boolean close(long sessionId, String instructorEmail);
}
