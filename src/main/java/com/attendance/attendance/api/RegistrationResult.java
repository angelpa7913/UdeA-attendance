package com.attendance.attendance.api;

import java.time.Instant;

/**
 * <h2>Propósito</h2>
 * Resultado tipado del registro: cada caso del flujo normal es un record.
 *
 * <h2>Responsabilidad SOLID</h2>
 * O: agregar un caso = agregar un record; el compilador obliga a manejarlo en cada {@code switch}.<br>
 * L: todos los casos se tratan igual como {@code RegistrationResult}.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Casos: {@code Registered(sessionId, registeredAt)}, {@code InvalidToken}, {@code SessionClosed}, {@code NotEnrolled}, {@code AlreadyRegistered}.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Agregar componentes a {@code Registered} (por ejemplo {@code long sessionId}, {@code Instant registeredAt}).</li>
 *   <li>Opcional: método {@code default String messageKey()} para la vista (o resolverlo en el controlador con {@code switch}).</li>
 *   <li>En el controlador usar {@code switch} con pattern matching sobre el sealed interface (sin {@code default}).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada (solo {@code java.time}).</li>
 * </ul>
 */
public sealed interface RegistrationResult {

    record Registered(long sessionId, Instant registeredAt) implements RegistrationResult {
    }

    record InvalidToken() implements RegistrationResult {
    }

    record SessionClosed() implements RegistrationResult {
    }

    record NotEnrolled() implements RegistrationResult {
    }

    record AlreadyRegistered() implements RegistrationResult {
    }
}
