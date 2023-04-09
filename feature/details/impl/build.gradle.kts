plugins {
    conventions.`module-compose-impl`
}

android {
    namespace = "odoo.miem.android.feature.details.impl"
}

dependencies {

    implementation(Dependencies.Compose.SwipableCard.SWIPABLE_CARD)

    api(project(":feature:details:api"))

    // Core
    // Theme
    implementation(project(":core:uiKitTheme"))

    // Common
    // Components
    implementation(project(":common:uiKitComponents"))
}