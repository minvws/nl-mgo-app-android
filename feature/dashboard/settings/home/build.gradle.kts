plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.settings.home"
}

dependencies {
  implementation(projects.framework.environment)
  implementation(projects.framework.storage)
  implementation(projects.data.pincode)
  implementation(projects.data.localisation)
  testImplementation(testFixtures(projects.framework.storage))
  testImplementation(testFixtures(projects.data.pincode))
  testImplementation(testFixtures(projects.data.localisation))
}
