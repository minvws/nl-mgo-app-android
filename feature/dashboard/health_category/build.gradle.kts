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
  implementation(projects.framework.environment)
  implementation(projects.data.fhir)
  implementation(projects.data.healthCategories)
  implementation(projects.data.hcimParser)
  implementation(projects.framework.fhir)
  implementation(projects.component.uiSchema)
  implementation(projects.component.healthCategories)
  implementation(projects.framework.storage)
  implementation(projects.component.organization)
  implementation(projects.component.error)
  implementation(projects.component.fhir)
  implementation(projects.data.organization)
  implementation(projects.component.pdf)
  testImplementation(libs.itext7.core)
  testImplementation(testFixtures(projects.data.hcimParser))
  testImplementation(testFixtures(projects.data.healthCategories))
  testImplementation(testFixtures(projects.data.fhir))
  testImplementation(testFixtures(projects.component.error))
  testImplementation(testFixtures(projects.data.organization))
}
