plugins {
    conventions.`module-api`
}

dependencies {

    // Core
    // Network Engine API - jsonrpc
    api(project(":core:networkEngine:jsonrpc:api"))
}