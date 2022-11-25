plugins {
    conventions.`module-compose-app`
}

dependencies {

    // Feature
    // Authorization
    implementation(project(":feature:authorization:base:api"))
    implementation(project(":feature:authorization:base:impl"))

    // Navigation
    implementation(project(":feature:navigation"))

    // Common
    // Network - authorization
    implementation(project(":common:network:authorization:impl"))

    // Core
    // DI
    implementation(project(":core:di:impl"))

    // Network API - authorization
    implementation(project(":core:networkApi:authorization:impl"))

    // Platform
    implementation(project(":core:platform"))

    // Ui Kit Theme
    implementation(project(":core:uiKitTheme"))
}