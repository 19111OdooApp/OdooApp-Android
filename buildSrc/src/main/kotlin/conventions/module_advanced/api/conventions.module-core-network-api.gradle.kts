/**
 * Convention for expansion [conventions.module-api] to provide network and RxJava dependencies
 * to :core:network modules
 *
 * @author Egor Danilov
 * @since 18.02.2023
 */
plugins {
    id("conventions.module-api")
}

dependencies {
    implementation(Dependencies.Network.MOSHI_KOTLIN)
    implementation(Dependencies.RxJava.RXJAVA_DEPENDENCE)
}