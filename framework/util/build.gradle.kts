plugins {
  id("AndroidFrameworkPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.framework.util"
  testFixtures {
    enable = true
  }
}

dependencies {
  implementation(libs.androidx.browser)
}
