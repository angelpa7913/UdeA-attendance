package com.attendance.session.application.port.out;

/**
 * <h2>Propósito</h2>
 * Puerto de salida: genera el secreto aleatorio de cada sesión.
 *
 * <h2>Responsabilidad SOLID</h2>
 * I/D: una operación; en pruebas se usa un generador fijo.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Salida: {@code String newSecret()} (Base64).</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar el método.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 */
public interface SecretGenerator {

    String newSecret();
}
