plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.core.networkApi.recruitment.impl"
}

dependencies {

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    // Core
    // Network API - authorization
    api(project(":core:networkApi:recruitment:api"))
    implementation(project(":core:jsonrpc:base"))

    // Retrofit Api Fabric
    implementation(project(":core:jsonRpcApiFabric:impl"))

    // Utils
    implementation(project(":core:utils"))
}