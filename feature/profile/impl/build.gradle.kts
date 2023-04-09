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

    // TODO Move
    implementation("me.saket.swipe:swipe:1.1.1")
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:1.1.1")
    implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:1.1.1")
}