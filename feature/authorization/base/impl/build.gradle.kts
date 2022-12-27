plugins {
    conventions.`module-compose-impl`
}

dependencies {

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    // Common
    // Components
    implementation(project(":common:uiKitComponents"))

    // Network - authorization
    api(project(":common:network:authorization:api"))

    // Core
    // UiKitTheme
    implementation(project(":core:uiKitTheme"))

    // Feature
    // Authorization API
    implementation(project(":feature:authorization:base:api"))

    // Navigation API
    implementation(project(":feature:navigation:api"))

    // Platform
    implementation(project(":core:platform"))
}