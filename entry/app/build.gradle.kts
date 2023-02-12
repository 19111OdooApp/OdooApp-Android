plugins {
    conventions.`module-compose-app`
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "odoo.miem.android"
}

dependencies {

    // Feature
    // Authorization
    implementation(project(":feature:authorization:base:api"))
    implementation(project(":feature:authorization:base:impl"))

    // Navigation
    implementation(project(":feature:navigation:impl"))

    // Profile
    implementation(project(":feature:profile:api"))
    implementation(project(":feature:profile:impl"))

    // Selecting Modules
    implementation(project(":feature:selectingModules:api"))
    implementation(project(":feature:selectingModules:impl"))

    // Recruitment
    implementation(project(":feature:recruitment:api"))
    implementation(project(":feature:recruitment:impl"))

    // Module Not Found
    implementation(project(":feature:moduleNotFound:api"))
    implementation(project(":feature:moduleNotFound:impl"))

    // Common
    // Network - authorization
    implementation(project(":common:network:authorization:impl"))

    // Network - selecting modules
    implementation(project(":common:network:selectingModules:impl"))

    // Core
    // Datastore
    implementation(project(":core:dataStore:impl"))

    // DI
    implementation(project(":core:di:impl"))

    // Network API - authorization
    implementation(project(":core:networkApi:authorization:impl"))

    // Network API - user info
    implementation(project(":core:networkApi:userInfo:impl"))

    // Network API - user modules
    implementation(project(":core:networkApi:userModules:impl"))

    // Network API - remote config
    implementation(project(":core:networkApi:remoteConfig:impl"))

    // Json RPC - Engine
    implementation(project(":core:jsonrpc:engine"))

    // Json RPC - Parser
    implementation(project(":core:jsonrpc:parser:impl"))

    // Platform
    implementation(project(":core:platform"))

    // Ui Kit Theme
    implementation(project(":core:uiKitTheme"))
    implementation(project(":core:utils"))
}