plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.common.networkapi.authorization.impl"
}

dependencies {

    Dependencies.Network.ALL_DEPS.forEach { implementation(it) }

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    // Core
    // Network API - authorization
    api(project(":core:networkApi:authorization:api"))

    // Retrofit Api Fabric
    implementation(project(":core:retrofitApiFabric:impl"))

    // Utils
    implementation(project(":core:utils"))
}