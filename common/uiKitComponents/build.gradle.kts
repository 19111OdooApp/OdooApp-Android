plugins {
    conventions.`module-compose-impl`
}

android {
    namespace = "odoo.miem.android.common.uiKitComponents"
}

dependencies {
    
    implementation(Dependencies.Compose.SwipableCard.SWIPABLE_CARD)

    // Core
    // UiKitTheme
    implementation(project(":core:uiKitTheme"))
}