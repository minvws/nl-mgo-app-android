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
include(":framework:copy")
include(":framework:snapshots")
include(":framework:network")
include(":framework:test")
include(":framework:storage")
include(":framework:environment")
include(":data:onboarding")
include(":feature:dashboard:bottombar")
include(":feature:onboarding:introduction")
include(":feature:onboarding:proposition")
include(":data:digid")
include(":component:mgo")
include(":component:theme")
include(":framework:util")
include(":feature:dashboard:organizations")
include(":feature:dashboard:health_categories")
include(":feature:dashboard:health_category")
include(":feature:dashboard:uischema")
include(":feature:dashboard:settings:home")
include(":feature:dashboard:settings:display")
include(":feature:dashboard:settings:advanced")
include(":feature:dashboard:settings:about:home")
include(":feature:dashboard:settings:about:safety")
include(":feature:dashboard:settings:about:opensource")
include(":feature:dashboard:settings:about:accessibility")
include(":feature:digid")
include(":feature:dashboard:edit_overview")
include(":data:hcimParser")
include(":data:fhir")
include(":framework:fhir")
include(":data:healthCategories")
include(":component:healthCategories")
include(":component:uiSchema")
include(":data:pft")
include(":component:organization")
include(":component:fhir")
include(":component:error")
include(":data:organization")
include(":feature:localisation:manual")
include(":component:pdf")
include(":component:pdfViewer")
include(":framework:javascript")
