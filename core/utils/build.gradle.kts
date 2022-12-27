plugins {
    conventions.`module-impl`
}

android {
    buildFeatures {
        compose = true
    }

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = CompileVersions.COMPOSE_COMPILE_VERSION
    }
}

dependencies {

    Dependencies.RxJava.ALL_DEPS.forEach { implementation(it) }

    implementation(Dependencies.Compose.RxJava.RX_JAVA)

    // DataStore
    api(project(":core:dataStore:api"))
}