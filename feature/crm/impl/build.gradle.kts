plugins {
    conventions.`module-compose-impl`
}

android {
    namespace = "odoo.miem.android.feature.crm.impl"
}

dependencies {

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    // Common
    // Components
    implementation(project(":common:uiKitComponents"))

    // Network
    // CRM
    api(project(":common:network:crm:api"))

    // SelectingModules
    api(project(":common:network:selectingModules:api"))

    // Core
    // UiKitTheme
    implementation(project(":core:uiKitTheme"))

    // Feature
    // Selecting modules API
    implementation(project(":feature:crm:api"))

    // Platform
    implementation(project(":core:platform"))

    // Navigation API
    implementation(project(":feature:navigation:api"))

    // Utils
    implementation(project(":core:utils"))
}