// TODO Description
plugins {
    id("conventions.module-impl")
}

dependencies {

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    // Core
    // Utils
    implementation(project(":core:utils"))

    // Core
    // Retrofit Api Fabric
    implementation(project(":core:retrofitApiFabric:impl"))
}