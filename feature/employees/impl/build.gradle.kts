plugins {
    conventions.`module-compose-impl`
}

android {
    namespace = "odoo.miem.android.feature.employees.impl"
}

dependencies {

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    // Common
    // Components
    implementation(project(":common:uiKitComponents"))

    // Network
    // SelectingModules
    api(project(":common:network:employees:api"))

    // SelectingModules
    api(project(":common:network:selectingModules:api"))

    // Core
    // UiKitTheme
    implementation(project(":core:uiKitTheme"))

    // Feature
    // Employees API
    implementation(project(":feature:employees:api"))
    // Platform
    implementation(project(":core:platform"))
    // Navigation API
    implementation(project(":feature:navigation:api"))
}