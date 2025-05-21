plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.settings.about"
}

dependencies {
  implementation(projects.data.fhirParser)
  implementation(projects.framework.environment)
  testImplementation(testFixtures(projects.data.fhirParser))
  testImplementation(testFixtures(projects.framework.environment))
}
