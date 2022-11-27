plugins {
    conventions.`module-impl`
}

dependencies {

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    // DataStore
    api(project(":core:dataStore:api"))
}