plugins {
    conventions.`module-compose-impl`
}

android {
    namespace = "odoo.miem.android.feature.navigation.impl"
}

dependencies {

    // Core
    // Ui Kit Theme
    implementation(project(":core:uiKitTheme"))

    // Feature
    // Authorization API
    implementation(project(":feature:authorization:base:api"))

    // Navigation API
    implementation(project(":feature:navigation:api"))

    // Selecting Modules API
    implementation(project(":feature:selectingModules:api"))

    // Module Not Found API
    implementation(project(":feature:moduleNotFound:api"))

    // Module Recruitment
    implementation(project(":feature:recruitment:api"))
}