plugins {
    conventions.`module-impl`
}

dependencies {

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    implementation(Dependencies.Compose.RxJava.RX_JAVA)

    // DataStore
    api(project(":core:dataStore:api"))
}