package com.attendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * <h2>Propósito</h2>
 * Punto de entrada de Spring Boot. Arranca el monolito modular completo.
 *
 * <h2>Responsabilidad SOLID</h2>
 * S: solo arranca la aplicación. No declara beans ni configuración; eso vive en
 * {@code shared.config} y en el {@code infrastructure} de cada módulo.
 *
 * <h2>Entradas / salidas</h2>
 * Entrada: argumentos de línea de comandos (por ejemplo {@code --spring.profiles.active=dev}).
 * Salida: el contexto de Spring en ejecución.
 *
 * <h2>Pasos a implementar</h2>
 * Ya está completo: es código de arranque, no lógica de negocio.
 * {@code @ConfigurationPropertiesScan} registra {@code AttendanceProperties} sin {@code @Component}.
 * Los módulos se detectan solos: cada subpaquete directo de {@code com.attendance} es un módulo
 * para Spring Modulith.
 *
 * <h2>Depende de</h2>
 * Nada propio.
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class AttendanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttendanceApplication.class, args);
    }
}
