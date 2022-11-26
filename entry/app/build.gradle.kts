plugins {
    conventions.`module-compose-app`
    id("com.google.gms.google-services")
}

dependencies {

    // Feature
    // Authorization
    implementation(project(":feature:authorization:base:api"))
    implementation(project(":feature:authorization:base:impl"))

    // Navigation
    implementation(project(":feature:navigation"))

    // Core
    // DI
    implementation(project(":core:di:impl"))

    // Platform
    implementation(project(":core:platform"))

    // Ui Kit Theme
    implementation(project(":core:uiKitTheme"))
}