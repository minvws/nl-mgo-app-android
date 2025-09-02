plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.dashboard.editOverview"
}

dependencies {
  implementation(projects.data.localisation)
  implementation(projects.data.healthcare)
  implementation(projects.framework.storage)
  implementation(projects.component.healthCareCategory)
  implementation(libs.reoderable)
  testImplementation(testFixtures((projects.data.localisation)))
  testImplementation(testFixtures(projects.data.healthcare))
  testImplementation(testFixtures(projects.framework.storage))
}
