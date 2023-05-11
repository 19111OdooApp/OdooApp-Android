plugins {
    conventions.`module-common-network-api`
}

android {
    namespace = "odoo.miem.android.common.network.recruitment.api"
}

dependencies {

    // Components
    implementation(project(":common:uiKitComponents"))
}