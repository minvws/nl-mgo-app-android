plugins {
  id("AndroidDataPlugin")
  alias(libs.plugins.serializable)
}

android {
  namespace = "nl.rijksoverheid.mgo.data.fhir"
  testFixtures {
    enable = true
  }
}

dependencies {
  api(projects.framework.fhir)
  implementation(projects.framework.storage)
  testImplementation(projects.framework.test)
  testFixturesImplementation(projects.framework.fhir)
  testFixturesImplementation(libs.junit)
  testFixturesImplementation(libs.androidx.test.core)
  testFixturesImplementation(libs.okhttp)
  testFixturesImplementation(projects.framework.storage)
  testFixturesImplementation(testFixtures(projects.framework.test))
}
