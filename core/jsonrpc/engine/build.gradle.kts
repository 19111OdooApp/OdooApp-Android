plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.core.jsonrpc.engine"
}

dependencies {

    implementation(Dependencies.Network.OKHTTP)

    // Core
    // Json RPC - Base
    api(project(":core:jsonrpc:base"))
}