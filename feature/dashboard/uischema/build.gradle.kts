plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.dashboard.uiSchema"
}

dependencies {
  implementation(projects.data.healthcare)
  implementation(projects.data.localisation)
  testImplementation(testFixtures(projects.data.healthcare))
  testImplementation(testFixtures(projects.data.fhirParser))
}
