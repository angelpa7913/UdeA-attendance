package com.attendance.session.api;

import java.time.Instant;

/**
 * <h2>Propósito</h2>
 * Token QR vigente y el instante en que vence su ventana.
 *
 * <h2>Depende de</h2>
 * Nada (solo {@code java.time}).
 */
public record CurrentToken(String value, Instant expiresAt) {
}
