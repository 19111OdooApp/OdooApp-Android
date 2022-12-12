pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "Odoo App"



// Common слой
include(":common:network:authorization:api")
include(":common:network:authorization:impl")

include(":common:uiKitComponents")


// Core слов
include(":core:dataStore:api")
include(":core:dataStore:impl")

include(":core:di:api")
include(":core:di:impl")

include(":core:networkApi:authorization:api")
include(":core:networkApi:authorization:impl")

include(":core:platform")

include(":core:retrofitApiFabric:api")
include(":core:retrofitApiFabric:impl")

include(":core:uiKitTheme")

include(":core:utils")


// Entry слой
include(":entry:app")


// Feature слой
include(":feature:authorization:base:api")
include(":feature:authorization:base:impl")

include(":feature:navigation")
