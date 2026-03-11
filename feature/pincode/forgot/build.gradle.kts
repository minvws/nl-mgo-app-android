plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.pincode.forgot"
}

dependencies {
  implementation(projects.data.organization)
  implementation(projects.framework.storage)
  implementation(projects.component.organization)
  testImplementation(testFixtures(projects.framework.storage))
  testImplementation(testFixtures(projects.data.organization))
}
