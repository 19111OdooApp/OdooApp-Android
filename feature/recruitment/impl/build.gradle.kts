plugins {
    conventions.`module-compose-impl`
}

android {
    namespace = "odoo.miem.android.feature.recruitment.impl"
}

dependencies {

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    // Common
    // Components
    implementation(project(":common:uiKitComponents"))

    // Network
    // Recruitment
    api(project(":common:network:recruitment:api"))

    // SelectingModules
    api(project(":common:network:selectingModules:api"))

    // Core
    // UiKitTheme
    implementation(project(":core:uiKitTheme"))

    // Feature
    // Recruitment API
    implementation(project(":feature:recruitment:api"))

    // Platform
    implementation(project(":core:platform"))

    // Navigation API
    implementation(project(":feature:navigation:api"))

    // Utils
    implementation(project(":core:utils"))
}