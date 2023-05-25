plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.core.jsonrpc.engine"
}

dependencies {

    implementation(Dependencies.Network.OKHTTP)

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    // Core
    // Datastore
    api(project(":core:dataStore:api"))

    // Converter
    api(project(":core:converter:api"))

    // Json RPC - Base
    api(project(":core:jsonrpc:base"))
}