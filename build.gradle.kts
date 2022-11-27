plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.kotlin.android") apply false
    id("org.jetbrains.kotlin.jvm") apply false
    id("io.gitlab.arturbosch.detekt") version Dependencies.Plugins.DETEKT_VERSION
}

buildscript {
    dependencies {
        classpath(Dependencies.Firebase.GOOGLE_SERVICES)
        classpath(Dependencies.Firebase.FIREBASE_CRASHLYTICS_GRADLE_PLUGIN)
    }
}

tasks {
    val detektAll by registering(io.gitlab.arturbosch.detekt.Detekt::class) {
        parallel = true
        setSource(files(projectDir))
        include("**/*.kt")
        exclude("**/*.kts")
        exclude("**/resources/**")
        exclude("**/build/**")
        exclude("**/**est**")
        config.setFrom(files("${rootDir}/.detekt/detekt-config.yml"))
        buildUponDefaultConfig = false
        ignoreFailures = false
        autoCorrect = true
        dependencies {
            detektPlugins(Dependencies.Plugins.DETEKT_FORMATTING)
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}