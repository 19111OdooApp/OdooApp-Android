import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Базовая конвенция для api модулей, которая не содержит
 * подключаемые завивисимости
 *
 * @author Ворожцов Михаил
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
        apiVersion = CompileVersions.KOTLIN_VERSION
        languageVersion = CompileVersions.KOTLIN_VERSION
    }
}
