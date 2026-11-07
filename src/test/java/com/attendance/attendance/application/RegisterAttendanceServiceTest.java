package com.attendance.attendance.application;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * <h2>Propósito</h2>
 * Un escenario por cada caso de {@code RegistrationResult}.
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
 *   <li>{@code RegisterAttendanceService}</li>
 *   <li>Mocks de {@code ValidateTokenUseCase} y {@code CheckEnrollmentUseCase}</li>
 *   <li>{@code AttendanceRepository} en memoria</li>
 *   <li>{@code Clock.fixed}</li>
 * </ul>
 */
class RegisterAttendanceServiceTest {

    @Test
    @Disabled("TODO")
    void returnsInvalidTokenWhenValidationFails() {
        // ValidateTokenUseCase devuelve vacío → InvalidToken; no se consulta enrollment.
    }

    @Test
    @Disabled("TODO")
    void returnsSessionClosedWhenSessionIsClosed() {
        // ValidatedSession con open=false → SessionClosed.
    }

    @Test
    @Disabled("TODO")
    void returnsNotEnrolledWhenParticipantIsNotInCourse() {
        // CheckEnrollmentUseCase vacío → NotEnrolled; no se guarda nada.
    }

    @Test
    @Disabled("TODO")
    void returnsAlreadyRegisteredWhenRecordExists() {
        // exists → true → AlreadyRegistered.
    }

    @Test
    @Disabled("TODO")
    void returnsAlreadyRegisteredOnConcurrentInsert() {
        // exists → false pero save → false → AlreadyRegistered.
    }

    @Test
    @Disabled("TODO")
    void returnsRegisteredAndUsesClockTime() {
        // Todo válido → Registered con registeredAt == clock.instant().
    }
}
