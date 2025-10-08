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
  implementation(libs.dagger.hilt.android)
  implementation(projects.framework.fhir)
  testImplementation(projects.framework.test)
}
