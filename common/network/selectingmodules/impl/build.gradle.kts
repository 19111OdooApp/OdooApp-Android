plugins {
    conventions.`module-common-network-impl`
}

android {
    namespace = "odoo.miem.android.common.network.selectingModules.impl"
}

dependencies {

    // Firebase
    Dependencies.Firebase.MODULE_DEPS.forEach { implementation(it) }
    implementation(platform(Dependencies.Firebase.FIREBASE_BOM))

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
}