package com.attendance.session.domain;

/**
 * <h2>Propósito</h2>
 * Objeto de valor del token QR: {@code sessionId.window.signature}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo forma y lee el texto del token; no firma ni valida (eso es {@code TokenSigner}).<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Componentes: {@code long sessionId}, {@code long window}, {@code String signature}.</li>
 *   <li>{@code static Optional<QrToken> parse(String raw)}</li>
 *   <li>{@code String serialize()}</li>
 *   <li>{@code String signedData()} → {@code sessionId + "." + window} (lo que se firma).</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Agregar componentes; separador {@code .} como constante.</li>
 *   <li>{@code parse}: dividir en exactamente 3 partes; números válidos; firma no vacía. Cualquier fallo → {@code Optional.empty()} (sin excepciones hacia afuera).</li>
 *   <li>{@code serialize}: unir con el separador.</li>
 *   <li>// SECURITY: limitar la longitud máxima de {@code raw} antes de procesarlo.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada.</li>
 * </ul>
 */
public record QrToken() {
}
