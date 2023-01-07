plugins {
    conventions.`module-impl`
}

android {
    namespace = "odoo.miem.android.core.retrofitapifabric"
}

dependencies {

    Dependencies.Retrofit.ALL_DEPS.forEach { implementation(it) }

    // Core
    // DataStore
    api(project(":core:dataStore:api"))

    // Retrofit Fabric API
    api(project(":core:retrofitApiFabric:api"))
}