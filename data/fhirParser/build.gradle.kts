plugins {
  id("AndroidDataPlugin")
  alias(libs.plugins.serializable)
}

android {
  namespace = "nl.rijksoverheid.mgo.data.fhirParser"
  testFixtures {
    enable = true
  }
}

dependencies {
  implementation(libs.j2v8) { artifact { type = "aar" } }
  testImplementation(libs.json)
  implementation(projects.framework.util)
  testImplementation(testFixtures(projects.framework.util))
}
