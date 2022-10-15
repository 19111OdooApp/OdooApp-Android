plugins {
    conventions.`base-impl`
}

dependencies {

    // Coroutines
    implementation(Dependencies.Coroutines.CORE)

    // Общие библиотеки
    // Dagger 2
    implementation(Dependencies.Dagger.ANDROID)
    Dependencies.Dagger.KAPT_DEPS.forEach { kapt(it) }

    // Tests
    allTestImplementation(kotlin(Dependencies.Test.TEST_JUNIT))
    allTestImplementation(Dependencies.Test.JUNIT)
    testImplementation(Dependencies.Test.MOCKK)

    // Core слой
    // DI API
    api(project(":core:di:api"))
}

fun DependencyHandler.allTestImplementation(deps: Any) {
    testImplementation(deps)
    androidTestImplementation(deps)
}