plugins {
  id("AndroidFrameworkPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.framework.test"
  testFixtures {
    enable = true
  }
}

dependencies {
  testFixturesImplementation(libs.okhttp.mockwebserver)
  testFixturesImplementation(libs.junit)
  testFixturesImplementation(libs.coroutines.test)
  testFixturesImplementation(libs.okhttp)
  testFixturesImplementation(libs.androidx.test.runner)
  testFixturesImplementation(libs.androidx.test.core)
  testFixturesImplementation(libs.dagger.hilt.testing)
}
