plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.dashboard.healthCategories"
}

dependencies {
  implementation(projects.framework.storage)
  implementation(projects.data.healthCategories)
  implementation(projects.data.fhir)
  implementation(projects.component.healthCategories)
  implementation(projects.component.organization)
  implementation(projects.component.fhir)
  implementation(projects.component.error)
  implementation(projects.data.organization)
  testImplementation(testFixtures(projects.framework.storage))
  testImplementation(testFixtures(projects.data.healthCategories))
  testImplementation(testFixtures(projects.data.fhir))
  testImplementation(testFixtures(projects.component.error))
  testImplementation(testFixtures(projects.data.organization))
}
