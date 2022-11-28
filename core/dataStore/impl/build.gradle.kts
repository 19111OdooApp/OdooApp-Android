plugins {
    conventions.`module-impl`
}

dependencies {

    implementation(Dependencies.AndroidCore.CORE)

    // Core
    // DataStore API
    api(project(":core:dataStore:api"))

    // Platform
    implementation(project(":core:platform"))
}