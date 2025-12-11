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
  implementation(projects.component.uiSchema)
  implementation(projects.data.pft)
  implementation(projects.component.organization)
  testImplementation(testFixtures(projects.framework.util))
  testImplementation(testFixtures(projects.data.fhir))
  testImplementation(testFixtures(projects.data.hcimParser))
  testImplementation(testFixtures(projects.data.pft))
}
