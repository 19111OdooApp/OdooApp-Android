plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.core.networkEngine.parser.impl"
}

dependencies {

    implementation(Dependencies.Network.CONVERTER_MOSHI)
    implementation(Dependencies.Network.MOSHI_KOTLIN)

    // Core
    // Network API - authorization
    api(project(":core:networkEngine:parser:api"))
}