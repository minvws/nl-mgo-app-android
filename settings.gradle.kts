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
include(":feature:localisation:add_organization")
include(":feature:localisation:organization_search")
include(":feature:localisation:organization_list")
include(":feature:onboarding:introduction")
include(":feature:onboarding:proposition")
include(":data:api:load")
include(":data:api:dva")
include(":component:collapsablecard")
include(":component:results")
include(":data:uiSchema")
include(":data:healthcare")
include(":component:banner")
include(":feature:pincode:create")
include(":feature:pincode:confirm")
include(":feature:pincode:login")
include(":data:pincode")
include(":component:pincode")
include(":feature:pincode:biometric")

include(":feature:dashboard:remove_organization")
include(":feature:dashboard:organizations")
include(":feature:dashboard:health_categories")
include(":feature:dashboard:health_category")
include(":feature:dashboard:uischema_detail")
