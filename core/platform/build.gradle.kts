plugins {
    conventions.`module-compose-impl`
}
dependencies {
    implementation(Dependencies.Compose.Lifecycle.VIEW_MODEL)
    implementation(Dependencies.Compose.RxJava.RX_JAVA)
    implementation(project(":core:utils"))
}
