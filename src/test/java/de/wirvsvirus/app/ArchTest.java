package de.wirvsvirus.app;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("de.wirvsvirus.app");

        noClasses()
            .that()
                .resideInAnyPackage("de.wirvsvirus.app.service..")
            .or()
                .resideInAnyPackage("de.wirvsvirus.app.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..de.wirvsvirus.app.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
