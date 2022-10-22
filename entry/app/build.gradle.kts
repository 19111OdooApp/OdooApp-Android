plugins {
    conventions.`module-app`
}

dependencies {

    // Core слой
    // DI
    implementation(project(":core:di:impl"))

    // Platform
    implementation(project(":core:platform"))
}