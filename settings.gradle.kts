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
include(":common:uiKitComponents")

// Core слов
include(":core:di:api")
include(":core:di:impl")

include(":core:platform")

include(":core:uiKitTheme")

// Entry слой
include(":entry:app")

// Feature слой
include(":feature:authorization:base:api")
include(":feature:authorization:base:impl")

include(":feature:navigation")
