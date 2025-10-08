plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.dashboard.uiSchema"
}

dependencies {
  implementation(projects.data.healthcare)
  implementation(projects.data.localisation)
  implementation(projects.component.pdfViewer)
  implementation(projects.data.hcimParser)
  implementation(libs.compose.navigation)
  testImplementation(testFixtures(projects.data.healthcare))
  testImplementation(testFixtures(projects.data.fhirParser))
  testImplementation(testFixtures(projects.framework.util))
}
