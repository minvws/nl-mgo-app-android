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
    }
}

rootProject.name = "MijnGezondheidsOverzicht"
include(":app")
include(":component:theme")
include(":feature:onboarding")
include(":feature:splash")
include(":framework:navigation")
include(":framework:copy")
include(":framework:snapshots")
include(":framework:network")
