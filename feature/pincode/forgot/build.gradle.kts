plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.pincode.forgot"
}

dependencies {
  implementation(projects.data.localisation)
  implementation(projects.framework.storage)
  implementation(projects.component.organization)
  testImplementation(testFixtures(projects.framework.storage))
  testImplementation(testFixtures(projects.data.localisation))
}
