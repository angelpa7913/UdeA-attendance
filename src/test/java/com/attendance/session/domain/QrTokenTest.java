package com.attendance.session.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

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
    void serializeAndParseRoundTrip() {
        final QrToken token = new QrToken(12L, 345L, "firma_-1");

        assertThat(QrToken.parse(token.serialize())).contains(token);
    }

    @Test
    void rejectsWrongNumberOfParts() {
        assertThat(QrToken.parse("1.2")).isEmpty();
        assertThat(QrToken.parse("1.2.3.4")).isEmpty();
    }

    @Test
    void rejectsNonNumericParts() {
        assertThat(QrToken.parse("abc.2.sig")).isEmpty();
    }

    @Test
    void rejectsEmptySignature() {
        assertThat(QrToken.parse("1.2.")).isEmpty();
    }

    @Test
    void rejectsOversizedToken() {
        assertThat(QrToken.parse("1.2." + "a".repeat(500))).isEmpty();
    }

    @Test
    void timeWindowIsEpochSecondsDividedByDuration() {
        assertThat(TimeWindow.of(Instant.ofEpochSecond(100), 20).number()).isEqualTo(5);
        assertThat(TimeWindow.of(Instant.ofEpochSecond(119), 20).number()).isEqualTo(5);
        assertThat(TimeWindow.of(Instant.ofEpochSecond(120), 20).number()).isEqualTo(6);
    }
}
