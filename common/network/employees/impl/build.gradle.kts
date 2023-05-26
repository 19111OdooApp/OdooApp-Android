plugins {
    conventions.`module-common-network-impl`
}

android {
    namespace = "odoo.miem.android.common.network.employees.impl"
}

dependencies {

    // Common
    // Employees API
    api(project(":common:network:employees:api"))

    // common utils
    implementation(project(":common:utils"))

    // Core
    // Network API - employees
    api(project(":core:networkApi:employees:api"))

    // DataStore
    api(project(":core:dataStore:api"))
}