import gradle.kotlin.dsl.accessors._bf5355cea62077187e215896f3ff9977.implementation
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project

/**
 * Конвеция для impl модулей, которая содержит [conventions.base-api] и
 * добавляет необходимые зависимости
 *
 * @author Ворожцов Михаил
 * @since 09.10.2022
 */
plugins {
    id("conventions.base-impl")
}

dependencies {

    // Dagger 2
    implementation(Dependencies.Dagger.ANDROID)
    Dependencies.Dagger.KAPT_DEPS.forEach { kapt(it) }
    
    // Logger
    implementation(Dependencies.Logger.TIMBER)

    // Firebase
    implementation(platform(Dependencies.Firebase.FIREBASE_BOM))
    implementation(Dependencies.Firebase.FIREBASE_ANALYTICS)

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