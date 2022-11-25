plugins {
    conventions.`module-impl`
}

dependencies {

    api(project(":core:retrofitApiFabric:api"))

    Dependencies.Retrofit.ALL_DEPS.forEach { implementation(it) }
}