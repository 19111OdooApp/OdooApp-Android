plugins {
    conventions.`module-compose-impl`
}

android {
    namespace = "odoo.miem.android.core.platform"
}

dependencies {

    implementation(Dependencies.Compose.Lifecycle.VIEW_MODEL)
    implementation(Dependencies.Compose.RxJava.RX_JAVA)

    // Core
    // Utils
    implementation(project(":core:utils"))
}