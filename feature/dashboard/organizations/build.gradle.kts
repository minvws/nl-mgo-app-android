plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.organizations"
}

dependencies {
  implementation(projects.framework.storage)
  implementation(projects.component.organization)
  implementation(projects.data.organization)
  testImplementation(testFixtures(projects.framework.storage))
  testImplementation(testFixtures(projects.data.organization))
}
