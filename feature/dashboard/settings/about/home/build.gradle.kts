plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.settings.about"
}

dependencies {
  implementation(projects.framework.environment)
  implementation(projects.data.hcimParser)
  implementation(projects.data.pft)
  testImplementation(testFixtures(projects.framework.util))
  testImplementation(testFixtures(projects.framework.environment))
  testImplementation(testFixtures(projects.data.hcimParser))
}
