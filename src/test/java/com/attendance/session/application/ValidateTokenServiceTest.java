package com.attendance.session.application;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * <h2>Propósito</h2>
 * Escenarios de {@code ValidateTokenService} con {@code Clock.fixed} y un {@code TokenSigner} real o fake.
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
 *   <li>{@code ValidateTokenService}</li>
 *   <li>{@code SessionRepository} en memoria</li>
 *   <li>{@code HmacTokenSigner} o fake</li>
 * </ul>
 */
class ValidateTokenServiceTest {

    @Test
    @Disabled("TODO")
    void acceptsTokenFromCurrentWindow() {
        // Token de la ventana actual → ValidatedSession con open=true.
    }

    @Test
    @Disabled("TODO")
    void acceptsTokenWithinTolerance() {
        // Token de la ventana anterior con toleranceWindows=1 → válido.
    }

    @Test
    @Disabled("TODO")
    void rejectsTokenOutsideTolerance() {
        // Token de hace 2 ventanas con tolerancia 1 → vacío.
    }

    @Test
    @Disabled("TODO")
    void rejectsFutureWindow() {
        // Ventana mayor que la actual → vacío.
    }

    @Test
    @Disabled("TODO")
    void rejectsTamperedSignature() {
        // Un carácter de la firma cambiado → vacío.
    }

    @Test
    @Disabled("TODO")
    void rejectsUnknownSession() {
        // sessionId inexistente → vacío.
    }

    @Test
    @Disabled("TODO")
    void reportsClosedSession() {
        // Firma y ventana válidas pero sesión cerrada → ValidatedSession con open=false.
    }
}
