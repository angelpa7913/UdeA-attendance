package com.attendance.attendance.api;

/**
 * <h2>Propósito</h2>
 * Puerto de entrada: registrar la asistencia del usuario que escaneó el QR.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S/I: una operación. Devuelve un resultado tipado, no lanza excepciones en el flujo normal.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: {@code String rawToken}, {@code String participantEmail}.</li>
 *   <li>Salida: {@code RegistrationResult}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar {@code RegistrationResult register(String rawToken, String participantEmail);}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code RegistrationResult}</li>
 * </ul>
 */
public interface RegisterAttendanceUseCase {
}
