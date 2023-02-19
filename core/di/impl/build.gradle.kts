plugins {
    conventions.`base-impl`
}

android {
    namespace = "odoo.miem.android.core.di.impl"
}

dependencies {

    // Coroutines
    implementation(Dependencies.Coroutines.CORE)

    // Common libs
    // Dagger 2
    implementation(Dependencies.Dagger.ANDROID)
    Dependencies.Dagger.KAPT_DEPS.forEach { kapt(it) }

    // Tests
    allTestImplementation(kotlin(Dependencies.Test.TEST_JUNIT))
    allTestImplementation(Dependencies.Test.JUNIT)
    testImplementation(Dependencies.Test.MOCKK)

    // Core layer
    // DI API
    api(project(":core:di:api"))
}

fun DependencyHandler.allTestImplementation(deps: Any) {
    testImplementation(deps)
    androidTestImplementation(deps)
}