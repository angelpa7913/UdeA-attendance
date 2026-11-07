package com.attendance.identity.application;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * <h2>Propósito</h2>
 * Escenarios de {@code GetCurrentUserService}.
 *
 * <h2>Responsabilidad SOLID</h2>
 * Prueba unitaria: se ejecuta sin levantar Spring (Mockito o adaptadores en memoria).<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Entrada: escenarios descritos en cada método.</li>
 *   <li>Salida: aserciones sobre el resultado.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Crear los dobles de prueba (mocks o fakes en memoria) de cada puerto.</li>
 *   <li>Usar {@code Clock.fixed(...)} cuando el tiempo importe.</li>
 *   <li>Implementar cada escenario y quitar {@code @Disabled}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>{@code GetCurrentUserService}</li>
 *   <li>Fakes de {@code AuthenticatedUserProvider} e {@code InstructorDirectory}</li>
 * </ul>
 */
class GetCurrentUserServiceTest {

    @Test
    @Disabled("TODO")
    void returnsEmptyWhenNoAuthenticatedUser() {
        // Sin usuario autenticado → Optional.empty().
    }

    @Test
    @Disabled("TODO")
    void assignsInstructorRoleWhenDirectorySaysSo() {
        // InstructorDirectory devuelve true → role INSTRUCTOR.
    }

    @Test
    @Disabled("TODO")
    void assignsParticipantRoleByDefault() {
        // InstructorDirectory devuelve false → role PARTICIPANT.
    }
}
