plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.settings.about"
}

dependencies {
  implementation(projects.framework.environment)
  implementation(projects.data.hcimParser)
  testImplementation(testFixtures(projects.framework.environment))
  testImplementation(testFixtures(projects.data.hcimParser))
}
