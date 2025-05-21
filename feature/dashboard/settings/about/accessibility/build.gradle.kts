plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.settings.about.accessibility"
}

dependencies {
  implementation(projects.framework.environment)
  testImplementation(testFixtures(projects.framework.environment))
}
