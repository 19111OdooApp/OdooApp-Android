pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "Odoo App"


// Common слой
// TODO

// Core слов
include(":core:di:api")
include(":core:di:impl")

include(":core:platform:api")
include(":core:platform:impl")

// Entry слой
include(":entry:app")

// Feature слой
// TODO

