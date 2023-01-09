plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.core.networkEngine.jsonrpc.impl"
}

dependencies {

    implementation(Dependencies.Network.OKHTTP)

    // Core
    // Network Engine API - jsonrpc
    api(project(":core:networkEngine:jsonrpc:api"))

    // Network Engine API - parser
    api(project(":core:networkEngine:parser:api"))
}