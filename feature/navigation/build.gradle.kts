plugins {
    conventions.`module-compose-impl`
}

dependencies {

    // Core
    // Ui Kit Theme
    implementation(project(":core:uiKitTheme"))

    // Feature
    // Authorization API
    implementation(project(":feature:authorization:base:api"))
}