plugins {
    conventions.`module-impl`
}

android {

    namespace = "odoo.miem.android.core.utils"

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

    // Moshi for serializer
    implementation(Dependencies.Network.MOSHI_KOTLIN)

    // DataStore
    api(project(":core:dataStore:api"))
}