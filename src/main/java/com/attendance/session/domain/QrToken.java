package com.attendance.session.domain;

import java.util.Optional;

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
public record QrToken(long sessionId, long window, String signature) {

    private static final String SEPARATOR = ".";
    private static final String SEPARATOR_REGEX = "\\.";
    private static final int PARTS = 3;
    private static final int SIGNATURE_INDEX = 2;
    private static final int MAX_RAW_LENGTH = 200;
    private static final int KEEP_EMPTY_PARTS = -1;

    public static Optional<QrToken> parse(String raw) {
        // SECURITY: se limita la longitud antes de procesar datos que llegan desde afuera.
        if (raw == null || raw.length() > MAX_RAW_LENGTH) {
            return Optional.empty();
        }
        final String[] parts = raw.split(SEPARATOR_REGEX, KEEP_EMPTY_PARTS);
        if (parts.length != PARTS || parts[SIGNATURE_INDEX].isEmpty()) {
            return Optional.empty();
        }
        try {
            return Optional.of(new QrToken(Long.parseLong(parts[0]), Long.parseLong(parts[1]),
                    parts[SIGNATURE_INDEX]));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public String serialize() {
        return signedData() + SEPARATOR + signature;
    }

    public String signedData() {
        return sessionId + SEPARATOR + window;
    }
}
