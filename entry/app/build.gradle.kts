plugins {
    conventions.`module-compose-app`
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "odoo.miem.android"
}

dependencies {

    // Feature
    // Authorization
    implementation(project(":feature:authorization:base:api"))
    implementation(project(":feature:authorization:base:impl"))

    // Navigation
    implementation(project(":feature:navigation:impl"))

    // Selecting Modules
    implementation(project(":feature:selectingModules:api"))
    implementation(project(":feature:selectingModules:impl"))

    // Common
    // Network - authorization
    implementation(project(":common:network:authorization:impl"))

    // Core
    // Datastore
    implementation(project(":core:dataStore:impl"))

    // DI
    implementation(project(":core:di:impl"))

    // Network API - authorization
    implementation(project(":core:networkApi:authorization:impl"))

    // Platform
    implementation(project(":core:platform"))

    // Ui Kit Theme
    implementation(project(":core:uiKitTheme"))
    implementation(project(":core:utils"))
}