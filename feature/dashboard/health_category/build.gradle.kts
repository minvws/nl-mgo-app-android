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
  implementation(projects.component.pdfViewer)
  implementation(projects.data.fhir)
  implementation(projects.data.healthCategories)
  implementation(projects.data.hcimParser)
  implementation(projects.framework.fhir)
  testImplementation(testFixtures(projects.data.localisation))
  testImplementation(testFixtures(projects.data.fhirParser))
  testImplementation(testFixtures(projects.data.hcimParser))
  testImplementation(testFixtures(projects.data.healthCategories))
}
