plugins {
    conventions.`module-common-network-impl`
}

android {
    namespace = "odoo.miem.android.common.network.recruitment.impl"
}

dependencies {

    // Common
    // Recruitment API
    api(project(":common:network:recruitment:api"))

    // Components
    implementation(project(":common:uiKitComponents"))

    // Core
    // Recruitment API - Authorization
    api(project(":core:networkApi:recruitment:api"))
}