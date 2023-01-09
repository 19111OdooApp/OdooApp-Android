plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.core.networkEngine.jsonrpc.impl"
}

dependencies {

    // Core
    // Network Engine API
    api(project(":core:networkEngine:jsonrpc:api"))
}