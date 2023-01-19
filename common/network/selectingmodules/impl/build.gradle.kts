plugins {
    conventions.`module-common-network-impl`
}

android {
    namespace = "odoo.miem.android.common.network.selectingModules.impl"
}

dependencies {

    // Common
    // SelectingModules API
    api(project(":common:network:selectingModules:api"))

    // Core
    // DataStore
    api(project(":core:dataStore:api"))

    // Network API - Authorization
    api(project(":core:networkApi:selectingModules:api"))
}