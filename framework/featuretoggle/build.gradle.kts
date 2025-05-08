plugins {
  id("AndroidFrameworkPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.framework.featuretoggle"
  testFixtures {
    enable = true
  }
}

dependencies {
  implementation(projects.framework.storage)
  implementation(projects.framework.environment)
}
