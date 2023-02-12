plugins {
    conventions.`module-compose-impl`
}

android {
    namespace = "odoo.miem.android.feature.navigation.impl"
}

dependencies {

    // Firebase
    Dependencies.Firebase.MODULE_DEPS.forEach { implementation(it) }
    implementation(platform(Dependencies.Firebase.FIREBASE_BOM))

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
}