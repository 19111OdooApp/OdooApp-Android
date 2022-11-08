import org.gradle.kotlin.dsl.dependencies

/**
 * Конвеция для расширения [conventions.module-app] в andorid-compose состовляющую
 *
 * @author Ворожцов Михаил
 * @since 06.10.2022
 */
plugins {
    id("conventions.module-app")
}

android {
    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = CompileVersions.COMPOSE_COMPILE_VERSION
    }
}

dependencies {

    // Android Core
    Dependencies.AndroidCore.ALL_DEPS.forEach { implementation(it) }

    // // Compose
    Dependencies.Compose.ALL_DEPS.forEach { implementation(it) }
    Dependencies.Compose.Core.ALL_CORE_DEBUG_DEPS.forEach { debugImplementation(it) }

    // UI
    Dependencies.UI.ALL_DEPS.forEach { implementation(it) }
}