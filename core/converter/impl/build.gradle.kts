plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.core.jsonrpc.converter.impl"
}

dependencies {

    implementation(Dependencies.Network.MOSHI_KOTLIN)

    // Json RPC - Parser API
    api(project(":core:converter:api"))
}