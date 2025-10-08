plugins {
  id("AndroidDataPlugin")
  alias(libs.plugins.serializable)
}

android {
  namespace = "nl.rijksoverheid.mgo.data.hcimParser"
  testFixtures {
    enable = true
  }
}

dependencies {
  api(projects.framework.fhir)
  api(libs.quickjs.wrapper.android)
  testImplementation(libs.quickjs.wrapper.java)
  testImplementation(testFixtures(projects.data.hcimParser))
}
