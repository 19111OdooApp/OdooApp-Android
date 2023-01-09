plugins {
    conventions.`module-api`
}

dependencies {

    // Core
    // Network Engine API
    api(project(":core:networkEngine:jsonrpc:api"))
}