plugins {
    conventions.`module-common-network-impl`
}

android {
    namespace = "odoo.miem.android.common.network.crm.impl"
}

dependencies {

    // Common
    // Recruitment API
    api(project(":common:network:crm:api"))

    // Components
    implementation(project(":common:uiKitComponents"))

    // Core
    // Recruitment API - Authorization
    api(project(":core:networkApi:crm:api"))
}