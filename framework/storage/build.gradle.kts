plugins {
  id("AndroidFrameworkPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.framework.storage"
  testFixtures {
    enable = true
  }

  defaultConfig {
    testInstrumentationRunner = "nl.rijksoverheid.mgo.framework.test.HiltTestRunner"
  }

  buildFeatures {
    buildConfig = true
  }
}

dependencies {
  implementation(libs.androidx.security.crypto)
  api(libs.datastore.preference)
}
