plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.core.networkApi.firebaseDatabase.impl"
}

dependencies {

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    // Moshi
    implementation(Dependencies.Network.MOSHI_KOTLIN)

    // Firebase
    Dependencies.Firebase.DATABASE_DEPS.forEach { implementation(it) }
    implementation(platform(Dependencies.Firebase.FIREBASE_BOM))

    implementation(project(":core:jsonrpc:base"))

    // Core
    // Network API - firebase database
    api(project(":core:networkApi:firebaseDatabase:api"))

    // Retrofit Api Fabric
    implementation(project(":core:jsonRpcApiFabric:impl"))

    // Utils
    implementation(project(":core:utils"))
}