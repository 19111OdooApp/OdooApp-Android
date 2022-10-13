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
        config.setFrom(files("${rootDir}/detekt-config.yml"))
        parallel = false
        baseline = file("${rootDir}/baseline.xml")
        basePath = projectDir.path
        autoCorrect = true
        dependencies {
            detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${Dependencies.Plugins.DETEKT_VERSION}")
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}