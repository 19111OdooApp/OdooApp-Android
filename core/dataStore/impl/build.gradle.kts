plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.core.dataStore.impl"
}

dependencies {

    implementation(Dependencies.AndroidCore.CORE)

    // Core
    // DataStore API
    api(project(":core:dataStore:api"))

    // Platform
    implementation(project(":core:platform"))
}