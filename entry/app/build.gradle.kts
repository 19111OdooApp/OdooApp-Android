plugins {
    conventions.`module-app`
}

dependencies {

    // Compose
    Dependencies.Compose.ALL_DEPS.forEach { implementation(it) }
    Dependencies.Compose.Core.ALL_CORE_DEBUG_DEPS.forEach { debugImplementation(it) }

    // Core слой
    // DI
    implementation(project(":core:di:impl"))

    // Platform
    implementation(project(":core:platform"))
}