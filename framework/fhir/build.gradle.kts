plugins {
  id("AndroidFrameworkPlugin")
  alias(libs.plugins.serializable)
}

android {
  namespace = "nl.rijksoverheid.mgo.framework.fhir"
  testFixtures {
    enable = true
  }
}

dependencies {
  implementation(libs.androidx.browser)
}
