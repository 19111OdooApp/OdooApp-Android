plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.core.networkApi.userModules.impl"
}

dependencies {

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    implementation(project(":core:jsonrpc:base"))

    // Core
    // Network API - user modules
    api(project(":core:networkApi:userModules:api"))

    // Network API - remote config
    api(project(":core:networkApi:remoteConfig:api"))

    // Retrofit Api Fabric
    implementation(project(":core:jsonRpcApiFabric:impl"))

    // Utils
    implementation(project(":core:utils"))
}