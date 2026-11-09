package com.attendance.session.domain;

import java.time.Instant;

/**
 * <h2>Propósito</h2>
 * Objeto de valor: número de ventana de tiempo a partir de un {@code Instant} y una duración.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo aritmética de tiempo. Hace posible la rotación sin hilos: {@code window = epochSeconds / windowSeconds}.<br>
 *
 * <h2>Entradas / salidas</h2>
 * <ul>
 *   <li>Componentes: {@code long number}.</li>
 *   <li>{@code static TimeWindow of(Instant instant, long windowSeconds)}</li>
 *   <li>{@code boolean isWithinTolerance(TimeWindow current, int toleranceWindows)} → {@code current - tolerance <= number <= current}</li>
 *   <li>{@code Instant end(long windowSeconds)} → para la cuenta regresiva.</li>
 * </ul>
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Validar {@code windowSeconds > 0}.</li>
 *   <li>Ventanas futuras NO son válidas (number &gt; current → false).</li>
 *   <li>Probar con instantes fijos en {@code QrTokenTest}/{@code ValidateTokenServiceTest}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * <ul>
 *   <li>Nada (solo {@code java.time}).</li>
 * </ul>
 */
public record TimeWindow(long number) {

    public static TimeWindow of(Instant instant, long windowSeconds) {
        requirePositive(windowSeconds);
        return new TimeWindow(Math.floorDiv(instant.getEpochSecond(), windowSeconds));
    }

    /** Las ventanas futuras nunca son válidas. */
    public boolean isWithinTolerance(TimeWindow current, int toleranceWindows) {
        return number <= current.number && number >= current.number - toleranceWindows;
    }

    public Instant end(long windowSeconds) {
        requirePositive(windowSeconds);
        return Instant.ofEpochSecond((number + 1) * windowSeconds);
    }

    private static void requirePositive(long windowSeconds) {
        if (windowSeconds <= 0) {
            throw new IllegalArgumentException("La duración de la ventana debe ser positiva");
        }
    }
}
