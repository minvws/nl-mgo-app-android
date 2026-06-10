plugins {
  id("AndroidFrameworkPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.framework.environment"
  testFixtures {
    enable = true
  }
}

dependencies {
  implementation(projects.framework.storage)
}
