plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.core.networkEngine.parser.impl"
}

dependencies {

    implementation(Dependencies.Retrofit.CONVERTER_MOSHI)
    implementation(Dependencies.Retrofit.MOSHI_KOTLIN)

    // Core
    // Network API - authorization
    api(project(":core:networkEngine:parser:api"))
}