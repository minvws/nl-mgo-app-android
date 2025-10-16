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
  testFixturesImplementation(libs.kotlinx.serialization.json)
}
