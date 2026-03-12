plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.settings.home"
}

dependencies {
  implementation(projects.framework.environment)
  implementation(projects.framework.storage)
  implementation(projects.component.organization)
  implementation(projects.data.healthCategories)
  implementation(projects.data.organization)
  testImplementation(testFixtures(projects.framework.storage))
  testImplementation(testFixtures(projects.data.organization))
}
