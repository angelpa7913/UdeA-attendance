package com.attendance;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * <h2>Propósito</h2>
 * Verifica con Spring Modulith que ningún módulo use partes internas de otro
 * (solo su paquete {@code api}) y que no haya ciclos entre módulos.
 *
 * <h2>Responsabilidad SOLID</h2>
 * Protege la D y la S a nivel de módulos: el diseño se valida en cada build, no solo en revisiones.
 *
 * <h2>Entradas / salidas</h2>
 * Entrada: las clases bajo {@code com.attendance}. Salida: la prueba pasa o lista las violaciones.
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Constante {@code ApplicationModules modules = ApplicationModules.of(AttendanceApplication.class)}.</li>
 *   <li>{@code verifiesModularStructure}: llamar {@code modules.verify()}.</li>
 *   <li>{@code writesDocumentation} (opcional): {@code new Documenter(modules).writeDocumentation()}
 *       para generar diagramas en {@code target/spring-modulith-docs}.</li>
 *   <li>Quitar {@code @Disabled} cuando exista al menos el módulo {@code identity}.</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * {@code spring-modulith-starter-test}. No levanta el contexto de Spring.
 */
class ModularityTest {

    @Test
    @Disabled("TODO")
    void verifiesModularStructure() {
        // Debe fallar si un módulo importa domain/application/infrastructure de otro módulo,
        // o si hay dependencias cíclicas entre módulos.
    }

    @Test
    @Disabled("TODO")
    void writesDocumentation() {
        // Debe generar la documentación de módulos (C4/PlantUML) sin lanzar errores.
    }
}
