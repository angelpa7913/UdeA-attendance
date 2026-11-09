package com.attendance;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.simpleNameEndingWith;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;

import org.springframework.beans.factory.annotation.Autowired;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

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
@AnalyzeClasses(packages = "com.attendance", importOptions = ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {

    private static final String DOMAIN = "com.attendance..domain..";
    private static final String API = "com.attendance..api..";
    private static final String APPLICATION = "com.attendance..application..";
    private static final String INFRASTRUCTURE = "com.attendance..infrastructure..";

    @ArchTest
    static final ArchRule domainDoesNotDependOnSpringOrJpa = noClasses()
            .that().resideInAPackage(DOMAIN)
            .should().dependOnClassesThat()
            .resideInAnyPackage("org.springframework..", "jakarta.persistence..", "org.hibernate..");

    @ArchTest
    static final ArchRule domainDoesNotDependOnOuterLayers = noClasses()
            .that().resideInAPackage(DOMAIN)
            .should().dependOnClassesThat().resideInAnyPackage(APPLICATION, INFRASTRUCTURE, API);

    @ArchTest
    static final ArchRule applicationDoesNotDependOnInfrastructure = noClasses()
            .that().resideInAPackage(APPLICATION)
            .should().dependOnClassesThat().resideInAPackage(INFRASTRUCTURE);

    @ArchTest
    static final ArchRule controllersDoNotUseRepositories = noClasses()
            .that().resideInAPackage("com.attendance..infrastructure.web..")
            .should().dependOnClassesThat()
            .resideInAnyPackage("com.attendance..infrastructure.persistence..", "com.attendance..application.port.out..");

    @ArchTest
    static final ArchRule noFieldInjection = noFields().should().beAnnotatedWith(Autowired.class);

    @ArchTest
    static final ArchRule useCasesAreInterfacesInApi = classes()
            .that().haveSimpleNameEndingWith("UseCase")
            .should().beInterfaces().andShould().resideInAPackage(API);

    @ArchTest
    static final ArchRule servicesImplementAUseCase = classes()
            .that().resideInAPackage(APPLICATION).and().haveSimpleNameEndingWith("Service")
            .should().implement(simpleNameEndingWith("UseCase"));
}
