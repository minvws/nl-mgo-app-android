plugins {
  id("AndroidDataPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.data.digid"
  testFixtures {
    enable = true
  }
}

dependencies {
  implementation(projects.framework.storage)
  implementation(projects.framework.environment)
  testImplementation(testFixtures(projects.framework.storage))
  testImplementation(testFixtures(projects.framework.environment))
}
