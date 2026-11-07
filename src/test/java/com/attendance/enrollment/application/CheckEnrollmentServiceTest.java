package com.attendance.enrollment.application;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * <h2>Propósito</h2>
 * Escenarios de {@code CheckEnrollmentService}.
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
 *   <li>{@code CheckEnrollmentService}</li>
 *   <li>{@code EnrollmentRepository} en memoria o mock</li>
 * </ul>
 */
class CheckEnrollmentServiceTest {

    @Test
    @Disabled("TODO")
    void returnsParticipantIdWhenEnrolled() {
        // Correo inscrito en el curso → Optional con su id.
    }

    @Test
    @Disabled("TODO")
    void returnsEmptyWhenNotEnrolled() {
        // Correo no inscrito → Optional.empty().
    }

    @Test
    @Disabled("TODO")
    void ignoresEmailCase() {
        // `Ana@X.COM` y `ana@x.com` se consideran el mismo participante.
    }
}
