plugins {
    conventions.`module-common-network-api`
}

android {
    namespace = "odoo.miem.android.common.network.crm.api"
}

dependencies {

    // Components
    implementation(project(":common:uiKitComponents"))
}