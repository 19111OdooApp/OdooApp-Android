import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

/**
 * Convention for api modules, which contains [conventions.base-api] and
 * pluggs in necessary dependencies
 *
 * @author Vorozhtsov Mikhail
 * @since 09.10.2022
 */
plugins {
    id("conventions.base-api")
}

dependencies {

    // DI
    api(project(":core:di:api"))
}