plugins {
    conventions.`module-compose-impl`
}

dependencies {

    // Common
    // Network - authorization
    api(project(":common:network:authorization:api"))

    // UiKitComponents
    implementation(project(":common:uiKitComponents"))


    // Core
    // UiKitTheme
    implementation(project(":core:uiKitTheme"))

    // Feature
    // Authorization API
    implementation(project(":feature:authorization:base:api"))

    // TODO Delete livedata
    implementation("androidx.compose.runtime:runtime-livedata:1.3.1")
}