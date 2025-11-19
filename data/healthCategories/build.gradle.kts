plugins {
  id("AndroidDataPlugin")
  alias(libs.plugins.serializable)
}

android {
  namespace = "nl.rijksoverheid.mgo.data.healthCategories"
  testFixtures {
    enable = true
  }
}

dependencies {
  implementation(projects.framework.fhir)
  implementation(projects.framework.storage)
  implementation(projects.component.mgo)
  implementation(projects.component.organization)
  testFixturesImplementation(libs.kotlinx.serialization.json)
}
