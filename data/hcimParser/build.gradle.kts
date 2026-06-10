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
  implementation(projects.framework.javascript)
  testImplementation(testFixtures(projects.framework.javascript))
  testFixturesImplementation(testFixtures(projects.framework.javascript))
}
