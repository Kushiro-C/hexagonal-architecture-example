package com.example.demo;

import com.example.demo.domain.DomainService;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "com.example.demo", importOptions = ImportOption.DoNotIncludeTests.class)
public class ArchitectureTests {

    private static final String BASE_PACKAGE = "com.example.demo";

    private static final String DOMAIN_PACKAGE = BASE_PACKAGE + ".domain";
    private static final String DOMAIN_API_PACKAGE = DOMAIN_PACKAGE + ".api";
    private static final String DOMAIN_MODEL_PACKAGE = DOMAIN_PACKAGE + ".model";
    private static final String DOMAIN_SERVICE_PACKAGE = DOMAIN_PACKAGE + ".service";
    private static final String DOMAIN_SPI_PACKAGE = DOMAIN_PACKAGE + ".spi";

    private static final String APPLICATION_PACKAGE = BASE_PACKAGE + ".application";

    private static final String INFRASTRUCTURE_PACKAGE = BASE_PACKAGE + ".infrastructure";

    @ArchTest
    public static final ArchRule hexagonal_architecture_principles = onionArchitecture()
            .domainModels(DOMAIN_MODEL_PACKAGE + "..")
            .domainServices(DOMAIN_SERVICE_PACKAGE + "..", DOMAIN_API_PACKAGE + "..", DOMAIN_SPI_PACKAGE + "..")
            .applicationServices(BASE_PACKAGE + ".runtime..")
            .adapter("application", APPLICATION_PACKAGE + "..")
            .adapter("infrastructure", INFRASTRUCTURE_PACKAGE + "..");

    @ArchTest
    public static final ArchRule api_only_interface =
            classes().that().resideInAPackage(DOMAIN_API_PACKAGE + "..").should().beInterfaces().allowEmptyShould(true);

    @ArchTest
    public static final ArchRule spi_only_interface =
            classes().that().resideInAPackage(DOMAIN_SPI_PACKAGE + "..").should().beInterfaces().allowEmptyShould(true);

    @ArchTest
    static final ArchRule all_api_should_be_in_package_api =
            noClasses()
                    .that().haveNameMatching(".*Api")
                    .should().resideOutsideOfPackage(DOMAIN_API_PACKAGE)
                    .allowEmptyShould(true);

    @ArchTest
    static final ArchRule all_spi_should_be_in_package_spi =
            noClasses()
                    .that().haveNameMatching(".*Spi")
                    .should().resideOutsideOfPackage(DOMAIN_SPI_PACKAGE)
                    .allowEmptyShould(true);

    @ArchTest
    static final ArchRule domain_api_should_contains_only_interface_whitout_default =
            noMethods()
                    .that().areDeclaredInClassesThat().resideInAPackage(DOMAIN_API_PACKAGE + "..")
                    .and().areDeclaredInClassesThat().areInterfaces()
                    .should().notHaveModifier(JavaModifier.ABSTRACT)
                    .allowEmptyShould(true);

    @ArchTest
    static final ArchRule domain_spi_should_contains_only_interface_whitout_default =
            noMethods()
                    .that().areDeclaredInClassesThat().resideInAPackage(DOMAIN_SPI_PACKAGE + "..")
                    .and().areDeclaredInClassesThat().areInterfaces()
                    .should().notHaveModifier(JavaModifier.ABSTRACT)
                    .allowEmptyShould(true);

    @ArchTest
    public static final ArchRule service_annotated_with_domain_service =
            classes().that().resideInAPackage(DOMAIN_SERVICE_PACKAGE + "..")
                    .and().doNotHaveModifier(JavaModifier.SYNTHETIC)
                    .should().beAnnotatedWith(DomainService.class);

    @ArchTest
    public static final ArchRule domain_service_annotated_only_service =
            classes().that().areAnnotatedWith(DomainService.class)
                    .should().resideInAPackage(DOMAIN_SERVICE_PACKAGE + "..")
                    .allowEmptyShould(true);

    @ArchTest
    public static final ArchRule domain_has_dependencies_on_white_list =
            classes().that().resideInAPackage(DOMAIN_PACKAGE + "..").and().areNotAnnotations().and().areNotRecords()
                    .should().dependOnClassesThat().resideInAPackage(DOMAIN_PACKAGE + "..")
                    .orShould().dependOnClassesThat().resideInAPackage("java..");

    @ArchTest
    static final ArchRule service_calls_others_services_only_on_not_override_method =
            methods()
                    .that()
                    .areDeclaredInClassesThat().resideInAPackage(DOMAIN_SERVICE_PACKAGE + "..")
                    .and()
                    .areAnnotatedWith("Override")
                    .should()
                    .onlyBeCalled().byClassesThat().resideInAPackage(APPLICATION_PACKAGE + "..")
                    .allowEmptyShould(true);

    @ArchTest
    static final ArchRule application_layer_only_depends_on_api_and_model =
            noClasses().that().resideInAPackage(APPLICATION_PACKAGE + "..")
                    .should().dependOnClassesThat().resideInAPackage(DOMAIN_SPI_PACKAGE + "..")
                    .orShould().dependOnClassesThat().resideInAPackage(DOMAIN_SERVICE_PACKAGE + "..");

    @ArchTest
    static final ArchRule infrastructure_layer_only_depends_on_spi_and_model =
            noClasses().that().resideInAPackage(INFRASTRUCTURE_PACKAGE + "..")
                    .should().dependOnClassesThat().resideInAPackage(DOMAIN_API_PACKAGE + "..")
                    .orShould().dependOnClassesThat().resideInAPackage(DOMAIN_SERVICE_PACKAGE + "..");

    @ArchTest
    public static final ArchRule dto_form_isolation =
            noClasses().that().resideInAPackage(APPLICATION_PACKAGE + ".controller.dto.form..")
                    .should().dependOnClassesThat().resideInAPackage(APPLICATION_PACKAGE + ".dto.view..")
                    .allowEmptyShould(true);

    @ArchTest
    public static final ArchRule dto_view_isolation =
            noClasses().that().resideInAPackage(APPLICATION_PACKAGE + ".controller.dto.view..")
                    .should().dependOnClassesThat().resideInAPackage(APPLICATION_PACKAGE + ".dto.form..")
                    .allowEmptyShould(true);
}
