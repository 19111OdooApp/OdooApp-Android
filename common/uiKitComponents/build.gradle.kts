plugins {
    conventions.`module-compose-impl`
}

android {
    namespace = "odoo.miem.android.common.uiKitComponents"
}

dependencies {
    // Core
    // UiKitTheme
    implementation(project(":core:uiKitTheme"))
}