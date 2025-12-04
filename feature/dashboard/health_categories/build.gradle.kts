plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.dashboard.healthCategories"
}

dependencies {
  implementation(projects.data.localisation)
  implementation(projects.framework.storage)
  implementation(projects.data.healthCategories)
  implementation(projects.data.fhir)
  implementation(projects.component.healthCategories)
  implementation(projects.component.organization)
  testImplementation(testFixtures(projects.data.localisation))
  testImplementation(testFixtures(projects.framework.storage))
  testImplementation(testFixtures(projects.data.healthCategories))
  testImplementation(testFixtures(projects.data.fhir))
}
