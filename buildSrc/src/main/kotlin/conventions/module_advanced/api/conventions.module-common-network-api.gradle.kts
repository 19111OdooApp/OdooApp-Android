/**
 * Convention for expansion [conventions.base-impl] for to generalize
 * common dependencies in `common/network/-/api` modules
 *
 * @author Vorozhtsov Mikhail
 * @since 27.11.2022
 */
plugins {
    id("conventions.base-impl")
}

dependencies {

    implementation(Dependencies.RxJava.RXJAVA_DEPENDENCE)

    // Core
    // DI
    api(project(":core:di:api"))

    // Utils
    implementation(project(":core:utils"))
}