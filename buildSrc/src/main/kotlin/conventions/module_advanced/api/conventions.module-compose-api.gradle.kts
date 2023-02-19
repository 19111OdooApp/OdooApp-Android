/**
 * Convension for extending [conventions.base-impl] to provide Compose's function
 *
 * @author Ворожцов Михаил
 * @since 06.10.2022
 */
plugins {
    id("conventions.base-impl")
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

    implementation(Dependencies.Compose.Core.UI)

    implementation(Dependencies.Compose.Navigation.NAVIGATION_COMPOSE)

    // Core
    // DI
    api(project(":core:di:api"))
}
