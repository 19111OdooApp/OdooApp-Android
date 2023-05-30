import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

/**
 * Convention for impl modules, which containts [conventions.base-api] and
 * pluggs in necessary dependencies
 *
 * @author Vorozhtsov Mikhail
 * @since 09.10.2022
 */
plugins {
    id("conventions.base-impl")
}

dependencies {

    // Dagger 2
    implementation(Dependencies.Dagger.ANDROID)
    Dependencies.Dagger.KAPT_DEPS.forEach { kapt(it) }

    // Coroutines
    implementation(Dependencies.Coroutines.CORE)

    // Logger
    implementation(Dependencies.Logger.TIMBER)

    // Tests
    allTestImplementation(kotlin(Dependencies.Test.TEST_JUNIT))
    allTestImplementation(Dependencies.Test.JUNIT)
    testImplementation(Dependencies.Test.MOCKK)

    // DI
    implementation(project(":core:di:impl"))

}

fun DependencyHandler.allTestImplementation(deps: Any) {
    testImplementation(deps)
    androidTestImplementation(deps)
}