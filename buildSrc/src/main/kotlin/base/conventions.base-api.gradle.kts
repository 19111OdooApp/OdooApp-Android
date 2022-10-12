import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Базовая конвенция для api модулей
 *
 * @author Ворожцов Михаил
 * @since 09.10.2022
 */
plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = CompileVersions.JAVA_COMPILE_VERSION
    }
    sourceCompatibility = CompileVersions.JAVA_COMPILE_VERSION
    targetCompatibility = CompileVersions.JAVA_COMPILE_VERSION
}