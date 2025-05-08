plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.settings.security"
}

dependencies {
  implementation(projects.framework.environment)
  implementation(projects.framework.storage)
  implementation(projects.component.pincode)
  testImplementation(testFixtures(projects.framework.storage))
}
