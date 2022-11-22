plugins {
    conventions.`module-impl`
}

dependencies {

    api(project(":common:network:authorization:api"))

    Dependencies.Retrofit.ALL_DEPS.forEach { implementation(it) }

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }
}