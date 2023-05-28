import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Basic convention for app modules
 *
 * @author Vorozhtsov Mikhail
 * @since 09.10.2022
 */
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = CompileVersions.CURRENT_COMPILE_VERSION

    defaultConfig {
        applicationId = "odoo.miem.android"
        minSdk = CompileVersions.MINIMUM_COMPILE_VERSION
        targetSdk = CompileVersions.CURRENT_COMPILE_VERSION
        versionCode = 5
        versionName = "1.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = CompileVersions.JAVA_COMPILE_VERSION
        apiVersion = CompileVersions.KOTLIN_LANGUAGE_VERSION
        languageVersion = CompileVersions.KOTLIN_LANGUAGE_VERSION
        freeCompilerArgs = listOf("-Xjvm-default=enable")
    }
}