pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots") }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots") }
    }
}

rootProject.name = "Odoo App"



// Common layer
include(":common:network:authorization:api")
include(":common:network:authorization:impl")

include(":common:network:selectingModules:api")
include(":common:network:selectingModules:impl")

include(":common:uiKitComponents")


// Core layer
include(":core:dataStore:api")
include(":core:dataStore:impl")

include(":core:di:api")
include(":core:di:impl")

include(":core:networkApi:authorization:api")
include(":core:networkApi:authorization:impl")

include(":core:networkApi:userInfo:api")
include(":core:networkApi:userInfo:impl")

include(":core:networkApi:userModules:api")
include(":core:networkApi:userModules:impl")

include(":core:networkApi:firebaseRemoteConfig:impl")
include(":core:networkApi:firebaseRemoteConfig:api")

include(":core:networkApi:firebaseDatabase:api")
include(":core:networkApi:firebaseDatabase:impl")

include(":core:jsonrpc:base")
include(":core:jsonrpc:core")
include(":core:jsonrpc:engine")

include(":core:jsonRpcApiFabric:api")
include(":core:jsonRpcApiFabric:impl")

include(":core:serializer:api")
include(":core:serializer:impl")

include(":core:platform")

include(":core:uiKitTheme")

include(":core:utils")

// Entry layer
include(":entry:app")


// Feature layer
include(":feature:authorization:base:api")
include(":feature:authorization:base:impl")

include(":feature:navigation:api")
include(":feature:navigation:impl")

include(":feature:details:api")
include(":feature:details:impl")

include(":feature:selectingModules:api")
include(":feature:selectingModules:impl")

include(":feature:moduleNotFound:api")
include(":feature:moduleNotFound:impl")

include(":feature:recruitment:api")
include(":feature:recruitment:impl")

include(":feature:crm:api")
include(":feature:crm:impl")

include(":feature:userProfile:api")
include(":feature:userProfile:impl")
