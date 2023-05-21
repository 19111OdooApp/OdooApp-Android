plugins {
    conventions.`module-common-network-api`
}

android {
    namespace = "odoo.miem.android.common.network.recruitment.api"
}

dependencies {

    // Network API - recruitment
    api(project(":core:networkApi:recruitment:api"))

    // Components
    implementation(project(":common:uiKitComponents"))
}