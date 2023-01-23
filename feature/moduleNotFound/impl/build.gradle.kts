plugins {
    conventions.`module-compose-impl`
}

android {
    namespace = "odoo.miem.android.feature.moduleNotFound.impl"
}

dependencies {

    // Common
    // Components
    implementation(project(":common:uiKitComponents"))

    // Core
    // UiKitTheme
    implementation(project(":core:uiKitTheme"))

    // Feature
    // Selecting modules API
    implementation(project(":feature:moduleNotFound:api"))
    // Platform
    implementation(project(":core:platform"))
}