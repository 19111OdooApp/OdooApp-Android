plugins {
    conventions.`module-compose-impl`
}

android {
    namespace = "odoo.miem.android.feature.profile.impl"
}

dependencies {

    api(project(":feature:profile:api"))

    // Core
    // Theme
    implementation(project(":core:uiKitTheme"))

    // Common
    // Components
    implementation(project(":common:uiKitComponents"))

    implementation("me.saket.swipe:swipe:1.1.1")
}