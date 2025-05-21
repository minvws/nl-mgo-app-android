plugins {
  id("AndroidDataPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.data.healthcare"
  testFixtures {
    enable = true
  }
}

dependencies {
  api(projects.data.fhirParser)
  implementation(projects.data.localisation)
  implementation(projects.data.fhirParser)
  implementation(projects.data.api.dva)
  implementation(projects.framework.copy)
  implementation(projects.framework.storage)
  testImplementation(testFixtures(projects.data.localisation))
  testImplementation(testFixtures(projects.data.fhirParser))
  testImplementation(testFixtures(projects.framework.storage))
  testImplementation(testFixtures(projects.data.healthcare))
  testFixturesImplementation(projects.data.localisation)
  testFixturesImplementation(projects.data.fhirParser)
}
