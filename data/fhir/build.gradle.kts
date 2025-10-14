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
  implementation(projects.framework.storage)
  implementation(projects.framework.fhir)
  testImplementation(projects.framework.test)
  testFixturesImplementation(projects.framework.fhir)
}
