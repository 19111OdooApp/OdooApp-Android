plugins {
    conventions.`module-compose-app`
}

dependencies {

    // Feature
    // Navigation
    implementation(project(":feature:navigation"))

    // Core
    // DI
    implementation(project(":core:di:impl"))

    // Platform
    implementation(project(":core:platform"))
}