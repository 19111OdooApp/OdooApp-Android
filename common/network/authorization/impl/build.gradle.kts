plugins {
    conventions.`module-impl`
}

dependencies {

    Dependencies.Retrofit.ALL_DEPS.forEach { implementation(it) }

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    // Common
    // Authorization API
    api(project(":common:network:authorization:api"))


    // Core
    // Network API
    implementation(project(":core:networkApi"))

    // Retrofit Api Fabric
    implementation(project(":core:retrofitApiFabric:impl"))
}