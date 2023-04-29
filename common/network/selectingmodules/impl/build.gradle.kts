plugins {
    conventions.`module-common-network-impl`
}

android {
    namespace = "odoo.miem.android.common.network.selectingModules.impl"
}

dependencies {

    // Moshi for serializing
    implementation(Dependencies.Network.MOSHI_KOTLIN)

    // Common
    // SelectingModules API
    api(project(":common:network:selectingModules:api"))

    // Core
    // Serializer
    api(project(":core:serializer:api"))

    // DataStore
    api(project(":core:dataStore:api"))

    // Network API - user info
    api(project(":core:networkApi:userInfo:api"))

    // Network API - user modules
    api(project(":core:networkApi:userModules:api"))

    // Network API - remote config
    api(project(":core:networkApi:firebaseRemoteConfig:api"))

    // Network API - firebase database
    api(project(":core:networkApi:firebaseDatabase:api"))
}