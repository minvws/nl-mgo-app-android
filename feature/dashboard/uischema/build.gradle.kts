plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.dashboard.uiSchema"
}

dependencies {
  implementation(projects.data.localisation)
  implementation(projects.component.pdfViewer)
  implementation(projects.data.hcimParser)
  implementation(projects.data.fhir)
  implementation(libs.compose.navigation)
  testImplementation(testFixtures(projects.data.fhirParser))
  testImplementation(testFixtures(projects.framework.util))
  testImplementation(testFixtures(projects.data.fhir))
  testImplementation(testFixtures(projects.data.hcimParser))
}
