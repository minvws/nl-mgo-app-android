plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.organizations"
}

dependencies {
  implementation(projects.data.localisation)
  implementation(projects.framework.storage)
  implementation(projects.component.organization)
  testImplementation(testFixtures(projects.data.localisation))
  testImplementation(testFixtures(projects.framework.storage))
}
