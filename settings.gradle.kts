pluginManagement {
    includeBuild("build-logic")
    repositories {
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
        maven { setUrl("https://jitpack.io") }
    }
}

rootProject.name = "MijnGezondheidsOverzicht"
include(":app")
include(":component:theme")
include(":feature:onboarding")
include(":feature:dashboard")
include(":feature:config")
include(":framework:navigation")
include(":framework:copy")
include(":framework:snapshots")
include(":framework:network")
include(":framework:test")
include(":framework:storage")
include(":framework:environment")
include(":data:config")
include(":data:onboarding")
