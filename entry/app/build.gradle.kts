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

    // Selecting Modules
    implementation(project(":feature:selectingModules:api"))
    implementation(project(":feature:selectingModules:impl"))

    // Recruitment
    implementation(project(":feature:recruitment:api"))
    implementation(project(":feature:recruitment:impl"))

    // CRM
    implementation(project(":feature:crm:api"))
    implementation(project(":feature:crm:impl"))

    // User Profile
    implementation(project(":feature:userProfile:api"))
    implementation(project(":feature:userProfile:impl"))

    // Module Not Found
    implementation(project(":feature:moduleNotFound:api"))
    implementation(project(":feature:moduleNotFound:impl"))

    // Employees
    implementation(project(":feature:employees:api"))
    implementation(project(":feature:employees:impl"))

    // Common
    // Network - authorization
    implementation(project(":common:network:authorization:impl"))

    // Network - recruitment
    implementation(project(":common:network:recruitment:impl"))

    // Network - crm
    implementation(project(":common:network:crm:impl"))

    // Network - selecting modules
    implementation(project(":common:network:selectingModules:impl"))

    // Network - employees
    implementation(project(":common:network:employees:impl"))

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

    // Network API - employees
    implementation(project(":core:networkApi:employees:impl"))

    // Network API - remote config
    implementation(project(":core:networkApi:firebaseRemoteConfig:impl"))

    // Network API - firebase datastore and storage
    implementation(project(":core:networkApi:firebaseDatabase:impl"))

    // Network API - recruitment
    implementation(project(":core:networkApi:recruitment:impl"))

    // Network API - crm
    implementation(project(":core:networkApi:crm:impl"))

    // Json RPC - Engine
    implementation(project(":core:jsonrpc:engine"))

    // Json RPC - Parser
    implementation(project(":core:converter:impl"))

    // Platform
    implementation(project(":core:platform"))

    // Ui Kit Theme
    implementation(project(":core:uiKitTheme"))
    implementation(project(":core:utils"))
}