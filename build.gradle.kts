plugins {
    id("com.android.application") apply false
    id("com.android.library") apply false
    id("org.jetbrains.kotlin.android") apply false
    id("org.jetbrains.kotlin.jvm") apply false
    id("io.gitlab.arturbosch.detekt") version Dependencies.Plugins.DETEKT_VERSION
}

allprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        source = files(
            "src/main/java",
            "gensrc/main/kotlin"
        )
        config.setFrom(files("${rootDir}/.detekt/detekt-config.yml"))
        parallel = false
        baseline = file("${rootDir}/.detekt/baseline.xml")
        basePath = projectDir.path
        autoCorrect = true
        dependencies {
            detektPlugins(Dependencies.Plugins.DETEKT_FORMATTING)
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}