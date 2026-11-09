package com.attendance.shared.config;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <h2>Propósito</h2>
 * Declara el bean {@code java.time.Clock} que usa toda la aplicación para saber la hora.
 *
 * <h2>Responsabilidad SOLID</h2>
 * D: nadie llama a {@code Instant.now()} directamente; reciben un {@code Clock} por constructor.
 * Así, en pruebas se pasa {@code Clock.fixed(...)} y la ventana del QR es determinista.
 *
 * <h2>Entradas / salidas</h2>
 * Entrada: ninguna.
 * Salida: un bean {@code Clock} en UTC.
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Anotar la clase con {@code @Configuration(proxyBeanMethods = false)}.</li>
 *   <li>Método {@code @Bean Clock clock()} que devuelva {@code Clock.systemUTC()}.</li>
 *   <li>No agregar otros beans aquí (una configuración, un propósito).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * {@code java.time.Clock} y Spring (anotaciones de configuración).
 */
@Configuration(proxyBeanMethods = false)
public class ClockConfig {

    @Bean
    Clock clock() {
        return Clock.systemUTC();
    }
}
