plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.dashboard.healthCategories"
}

dependencies {
  implementation(projects.data.localisation)
  implementation(projects.data.healthcare)
  implementation(projects.framework.storage)
  implementation(projects.component.healthCareCategory)
  testImplementation(testFixtures((projects.data.localisation)))
  testImplementation(testFixtures(projects.data.healthcare))
  testImplementation(testFixtures(projects.framework.storage))
}
