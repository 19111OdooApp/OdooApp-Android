plugins {
    conventions.`module-compose-impl`
}

android {
    namespace = "odoo.miem.android.common.uiKitComponents"
}

dependencies {
    
    implementation(Dependencies.Compose.SwipableCard.SWIPABLE_CARD)
    implementation(Dependencies.Compose.HtmlText.HTML_TEXT)

    // Core
    // UiKitTheme
    implementation(project(":core:uiKitTheme"))

    // Common
    // utils
    implementation(project(":common:utils"))
}