plugins {
    conventions.`module-compose-impl`
}

android {
    namespace = "odoo.miem.android.feature.details.impl"
}

dependencies {

    api(project(":feature:details:api"))

    // Core
    // Theme
    implementation(project(":core:uiKitTheme"))

    // Common
    // Components
    implementation(project(":common:uiKitComponents"))

    // TODO Move
    implementation("me.saket.swipe:swipe:1.1.1")
}