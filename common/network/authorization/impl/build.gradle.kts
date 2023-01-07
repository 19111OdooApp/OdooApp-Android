plugins {
    conventions.`module-common-network-impl`
}

android {
    namespace = "odoo.miem.android.common.network.authorization.impl"
}

dependencies {

    // Common
    // Authorization API
    api(project(":common:network:authorization:api"))

    // Core
    // DataStore
    api(project(":core:dataStore:api"))

    // Network API - Authorization
    api(project(":core:networkApi:authorization:api"))
}