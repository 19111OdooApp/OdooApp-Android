plugins {
    conventions.`module-common-network-impl`
}

android {
    namespace = "odoo.miem.android.common.network.selectingModules.impl"
}

dependencies {

    // Moshi for serializer
    implementation(Dependencies.Network.MOSHI_KOTLIN)

    // Common
    // SelectingModules API
    api(project(":common:network:selectingModules:api"))

    // Core
    // DataStore
    api(project(":core:dataStore:api"))

    // Network API
    // user info
    api(project(":core:networkApi:userInfo:api"))

    // user modules
    api(project(":core:networkApi:userModules:api"))

    // remote config
    api(project(":core:networkApi:remoteConfig:api"))

    // firebase database
    api(project(":core:networkApi:firebaseDatabase:api"))
}