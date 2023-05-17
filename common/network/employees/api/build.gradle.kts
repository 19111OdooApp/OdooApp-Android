plugins {
    conventions.`module-common-network-api`
}

android {
    namespace = "odoo.miem.android.common.network.employees.api"
}

dependencies {

    // Components
    implementation(project(":common:uiKitComponents"))
}