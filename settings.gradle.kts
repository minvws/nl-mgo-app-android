enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

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
include(":data:localisation")
include(":feature:dashboard:bottombar")
include(":feature:dashboard:overview")
include(":feature:localisation:add_organization")
include(":feature:localisation:organization_search")
include(":feature:localisation:stored")
include(":feature:onboarding:introduction")
include(":feature:onboarding:proposition")
include(":feature:healthcareprovider:details")
include(":feature:healthcareprovider:medication")
include(":data:api:load")
include(":data:api:dva")
include(":data:medication")
include(":component:collapsablecard")
include(":feature:healthcareprovider:concern")
include(":data:concern")
include(":framework:fhir_extension")
include(":data:laboratoryTestResult")
include(":feature:healthcareprovider:laboratoryTestResult")
include(":component:results")
include(":feature:healthcareprovider:removeprovider")
