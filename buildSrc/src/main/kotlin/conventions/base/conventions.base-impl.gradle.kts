import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Basic conventions for api modules which doesn't contains
 * pluggable dependencies
 *
 * @author Vorozhtsov Mikhail
 * @since 15.09.2022
 */
plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = CompileVersions.CURRENT_COMPILE_VERSION

    defaultConfig {
        minSdk = CompileVersions.MINIMUM_COMPILE_VERSION
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = CompileVersions.JAVA_COMPILE_VERSION
        apiVersion = CompileVersions.KOTLIN_LANGUAGE_VERSION
        languageVersion = CompileVersions.KOTLIN_LANGUAGE_VERSION
    }
}
