plugins {
    conventions.`module-compose-impl`
}

dependencies {

    // Feature
    // Authorization API
    implementation(project(":feature:authorization:base:api"))
}