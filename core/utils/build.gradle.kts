plugins {
    conventions.`module-impl`
}

dependencies {

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }
}