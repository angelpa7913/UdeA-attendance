package com.attendance.session.domain;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * <h2>Propósito</h2>
 * Escenarios de {@code QrToken} y {@code TimeWindow} (puros, sin mocks).
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
 *   <li>{@code QrToken}, {@code TimeWindow}</li>
 * </ul>
 */
class QrTokenTest {

    @Test
    @Disabled("TODO")
    void serializeAndParseRoundTrip() {
        // parse(serialize(t)) devuelve un token igual a t.
    }

    @Test
    @Disabled("TODO")
    void rejectsWrongNumberOfParts() {
        // `1.2` o `1.2.3.4` → Optional.empty().
    }

    @Test
    @Disabled("TODO")
    void rejectsNonNumericParts() {
        // `abc.2.sig` → Optional.empty().
    }

    @Test
    @Disabled("TODO")
    void rejectsEmptySignature() {
        // `1.2.` → Optional.empty().
    }

    @Test
    @Disabled("TODO")
    void timeWindowIsEpochSecondsDividedByDuration() {
        // Instant 100 s con ventana 20 s → ventana 5; 119 s → 5; 120 s → 6.
    }
}
