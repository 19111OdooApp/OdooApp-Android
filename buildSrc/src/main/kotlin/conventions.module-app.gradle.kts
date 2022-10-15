/**
 * Конвеция для app модулей, которая содержит [conventions.base-api] и
 * добавляет необходимые зависимости
 *
 * @author Ворожцов Михаил
 * @since 09.10.2022
 */
plugins {
    id("conventions.base-app")
}

dependencies {
    // Core
    Dependencies.AndroidCore.ALL_DEPS.forEach { implementation(it) }

    // UI
    Dependencies.UI.ALL_DEPS.forEach { implementation(it) }

    // Dagger 2
    implementation(Dependencies.Dagger.ANDROID)
    Dependencies.Dagger.KAPT_DEPS.forEach { kapt(it) }

    // Logger
    implementation(Dependencies.Logger.TIMBER)

    // Tests
    allTestImplementation(kotlin(Dependencies.Test.TEST_JUNIT))
    allTestImplementation(Dependencies.Test.JUNIT)
    testImplementation(Dependencies.Test.MOCKK)
}

fun DependencyHandler.allTestImplementation(deps: Any) {
    testImplementation(deps)
    androidTestImplementation(deps)
}