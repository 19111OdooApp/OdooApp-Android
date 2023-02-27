import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Basic convention for api modules
 *
 * @author Vorozhtsov Mikhail
 * @since 09.10.2022
 */
plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = CompileVersions.JAVA_COMPILE_VERSION
        apiVersion = CompileVersions.KOTLIN_LANGUAGE_VERSION
        languageVersion = CompileVersions.KOTLIN_LANGUAGE_VERSION
    }
}