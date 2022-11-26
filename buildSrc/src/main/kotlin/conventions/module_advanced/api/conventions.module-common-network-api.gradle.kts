// TODO Description
plugins {
    id("conventions.base-impl")
}

dependencies {

    implementation(Dependencies.RxJava.RXJAVA)

    // Core
    // DI
    api(project(":core:di:api"))

    // Utils
    implementation(project(":core:utils"))
}