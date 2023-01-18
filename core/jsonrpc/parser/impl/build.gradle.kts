plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.core.jsonrpc.parser.impl"
}

dependencies {

    implementation(Dependencies.Network.MOSHI_KOTLIN)

    // Core
    // Json RPC - Base
    api(project(":core:jsonrpc:base"))

    // Json RPC - Parser API
    api(project(":core:jsonrpc:parser:api"))
}