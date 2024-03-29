plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.core.jsonrpcapifabric"
}

dependencies {

    implementation(Dependencies.Network.LOGGING_INTERCEPTOR)

    // Core
    // DataStore
    api(project(":core:dataStore:api"))

    // Json RPC - CORE
    implementation(project(":core:jsonrpc:core"))

    // Retrofit Fabric API
    api(project(":core:jsonRpcApiFabric:api"))
}