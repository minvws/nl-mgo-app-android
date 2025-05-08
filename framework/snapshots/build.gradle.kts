plugins {
  id("AndroidFrameworkPlugin")
  id("AndroidUiPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.framework.snapshots"
  testFixtures {
    enable = true
  }
}

dependencies {
  testFixturesImplementation(libs.paparazzi)
  testFixturesImplementation(libs.compose.material3)
}
