plugins {
    conventions.`module-impl`
}

dependencies {

    Dependencies.Retrofit.ALL_DEPS.forEach { implementation(it) }

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    api(project(":core:retrofitApiFabric:api"))
}