/**
 * Конвеция для расширения [conventions.module-impl] в android-compose состовляющую
 *
 * @author Ворожцов Михаил
 * @since 06.10.2022
 */
plugins {
    id("conventions.module-impl")
}

android {
    buildFeatures {
        compose = true
    }

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = CompileVersions.COMPOSE_COMPILE_VERSION
    }
}

dependencies {

    // Android Core
    Dependencies.AndroidCore.ALL_DEPS.forEach { implementation(it) }

    // Compose
    Dependencies.Compose.ALL_DEPS.forEach { implementation(it) }
    Dependencies.Compose.Core.ALL_CORE_DEBUG_DEPS.forEach { debugImplementation(it) }

    // Tests
    androidTestImplementation(Dependencies.Compose.UiTest.UI_TEST)
    debugImplementation(Dependencies.Compose.UiTest.UI_TEST_DEBUG)

    // UI
    Dependencies.UI.ALL_DEPS.forEach { implementation(it) }

    // Core
    // Utils
    implementation(project(":core:utils"))
}