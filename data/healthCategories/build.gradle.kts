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
  implementation(libs.datastore.preference)
  testFixturesImplementation(libs.kotlinx.serialization.json)
}
