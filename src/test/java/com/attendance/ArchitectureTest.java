package com.attendance;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * <h2>Propósito</h2>
 * Reglas de capas dentro de cada módulo (hexagonal), verificadas con ArchUnit.
 * Complementa a {@code ModularityTest}: aquel mira entre módulos, este mira dentro de cada uno.
 *
 * <h2>Responsabilidad SOLID</h2>
 * D: el {@code domain} y la {@code application} no dependen de detalles (Spring, JPA, web).
 * S: los controladores solo orquestan; no tocan repositorios.
 *
 * <h2>Entradas / salidas</h2>
 * Entrada: clases importadas con {@code @AnalyzeClasses(packages = "com.attendance",
 * importOptions = ImportOption.DoNotIncludeTests.class)}.
 * Salida: la prueba pasa o lista las violaciones.
 *
 * <h2>Pasos a implementar</h2>
 * <ol>
 *   <li>Cambiar los métodos por campos {@code @ArchTest static final ArchRule ...} (o mantener
 *       métodos que llamen {@code rule.check(classes)}).</li>
 *   <li>Usar {@code noClasses().that().resideInAPackage("..domain..")} para las reglas de dominio.</li>
 *   <li>Usar {@code ..infrastructure.web..} y {@code ..infrastructure.persistence..} para controladores.</li>
 *   <li>Quitar {@code @Disabled} a medida que existan las clases (una regla sin clases falla:
 *       usa {@code .allowEmptyShould(true)} mientras tanto).</li>
 * </ol>
 *
 * <h2>Depende de</h2>
 * {@code archunit-junit5}. No levanta el contexto de Spring.
 */
class ArchitectureTest {

    @Test
    @Disabled("TODO")
    void domainDoesNotDependOnSpringOrJpa() {
        // Ninguna clase en ..domain.. depende de org.springframework.., jakarta.persistence..
        // ni de org.hibernate..
    }

    @Test
    @Disabled("TODO")
    void domainDoesNotDependOnOuterLayers() {
        // ..domain.. no depende de ..application.., ..infrastructure.. ni ..api..
    }

    @Test
    @Disabled("TODO")
    void applicationDoesNotDependOnInfrastructure() {
        // ..application.. no depende de ..infrastructure.. (solo de puertos en port.out)
    }

    @Test
    @Disabled("TODO")
    void controllersDoNotUseRepositories() {
        // Clases en ..infrastructure.web.. no dependen de ..infrastructure.persistence..
        // ni de ..application.port.out.. (solo llaman casos de uso del api).
    }

    @Test
    @Disabled("TODO")
    void noFieldInjection() {
        // Ningún campo está anotado con @Autowired (inyección solo por constructor).
    }

    @Test
    @Disabled("TODO")
    void useCasesAreInterfacesInApi() {
        // Las clases cuyo nombre termina en "UseCase" son interfaces y residen en ..api..
    }

    @Test
    @Disabled("TODO")
    void servicesImplementAUseCase() {
        // Las clases en ..application.. cuyo nombre termina en "Service" implementan
        // al menos una interfaz que termina en "UseCase".
    }
}
