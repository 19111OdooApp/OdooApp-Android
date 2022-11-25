plugins {
    conventions.`module-common-network-impl`
}

dependencies {

    // Common
    // Authorization API
    api(project(":common:network:authorization:api"))

    // Core
    // Network API - Authorization
    api(project(":core:networkApi:authorization:api"))
}