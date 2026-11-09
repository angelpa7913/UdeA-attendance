package com.attendance.session.api;

/**
 * <h2>Propósito</h2>
 * Resultado tipado de abrir una sesión.
 *
 * <h2>Depende de</h2>
 * Nada.
 */
public sealed interface StartSessionResult {

    /** Sesión nueva abierta. */
    record Started(long sessionId) implements StartSessionResult {
    }

    /** El correo no es instructor de ese curso. */
    record NotCourseOwner() implements StartSessionResult {
    }

    /** El curso ya tenía una sesión abierta, que se reutiliza. */
    record AlreadyOpen(long sessionId) implements StartSessionResult {
    }
}
