package com.attendance.session.application.port.out;

/**
 * <h2>Propósito</h2>
 * Puerto de salida: firmar y verificar datos con el secreto de la sesión.
 *
 * <h2>Responsabilidad SOLID</h2>
 * I: dos operaciones. D: los servicios no saben que es HMAC-SHA256.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>{@code String sign(String data, String secret)} → firma Base64 URL-safe.</li>
 *   <li>{@code boolean verify(String data, String signature, String secret)}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Declarar ambos métodos.</li>
 *   <li>// SECURITY: la implementación debe comparar en tiempo constante.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 */
public interface TokenSigner {
}
