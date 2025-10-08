plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.dashboard.editOverview"
}

dependencies {
  implementation(projects.data.localisation)
  implementation(projects.framework.storage)
  implementation(projects.data.healthCategories)
  implementation(libs.reoderable)
  testImplementation(testFixtures((projects.data.localisation)))
  testImplementation(testFixtures(projects.framework.storage))
}
