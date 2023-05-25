plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.core.networkApi.remoteConfig.impl"
}

dependencies {

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    // Moshi
    implementation(Dependencies.Network.MOSHI_KOTLIN)

    // Firebase
    Dependencies.Firebase.MODULE_DEPS.forEach { implementation(it) }
    implementation(platform(Dependencies.Firebase.FIREBASE_BOM))

    implementation(project(":core:jsonrpc:base"))

    // Core
    // Network API - user info
    api(project(":core:networkApi:firebaseRemoteConfig:api"))

    // Retrofit Api Fabric
    implementation(project(":core:jsonRpcApiFabric:impl"))

    // Utils
    implementation(project(":core:utils"))
}