plugins {
  id("AndroidFeaturePlugin")
  alias(libs.plugins.serializable)
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.dashboard.healthCategory"
  testFixtures {
    enable = true
  }
}

dependencies {
  implementation(projects.data.localisation)
  implementation(projects.framework.environment)
  implementation(projects.data.healthcare)
  testImplementation(testFixtures(projects.data.localisation))
  testImplementation(testFixtures(projects.data.healthcare))
  testImplementation(testFixtures(projects.data.fhirParser))
}
