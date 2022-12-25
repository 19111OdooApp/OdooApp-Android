plugins {
    conventions.`module-compose-impl`
}

dependencies {

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    // Common
    // Components
    implementation(project(":common:uiKitComponents"))

    // Core
    // UiKitTheme
    implementation(project(":core:uiKitTheme"))
    // SharedElements
    implementation(project(":core:sharedElements"))

    // Feature
    // Selecting modules API
    implementation(project(":feature:selectingModules:api"))
}