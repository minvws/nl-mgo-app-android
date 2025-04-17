plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.onboarding.proposition"
}

dependencies {
  implementation(projects.data.onboarding)
  implementation(projects.framework.environment)
  testImplementation(testFixtures(projects.data.onboarding))
  testImplementation(testFixtures(projects.framework.environment))
}
