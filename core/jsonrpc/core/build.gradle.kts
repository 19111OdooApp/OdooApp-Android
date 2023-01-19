plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.core.jsonrpc.core"
}

dependencies {

    implementation(Dependencies.Network.OKHTTP)

    // Core
    // Json RPC - Base
    api(project(":core:jsonrpc:base"))

    // Json RPC - Engine
    implementation(project(":core:jsonrpc:engine"))

    // Json RPC - Parser API
    implementation(project(":core:jsonrpc:parser:api"))
}