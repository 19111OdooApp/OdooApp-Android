plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.common.networkapi.authorization.impl"
}

dependencies {

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    implementation(project(":core:jsonrpc:base"))

    // Core
    // Network API - authorization
    api(project(":core:networkApi:authorization:api"))

    // Retrofit Api Fabric
    implementation(project(":core:jsonRpcApiFabric:impl"))

    // Utils
    implementation(project(":core:utils"))
}