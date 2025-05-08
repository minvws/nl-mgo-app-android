plugins {
  id("AndroidDataPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.data.onboarding"
  testFixtures {
    enable = true
  }
}

dependencies {
  implementation(projects.framework.storage)
  testImplementation(testFixtures(projects.framework.storage))
}
