/**
 * Convention for expansion [conventions.module-impl] for to generalize
 * common dependencies in `common/network/-/impl` modules
 *
 * @author Vorozhtsov Mikhail
 * @since 27.11.2022
 */
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