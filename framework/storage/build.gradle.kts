plugins {
  id("AndroidFrameworkPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.framework.storage"
  testFixtures {
    enable = true
  }

  buildFeatures {
    buildConfig = true
  }
}

dependencies {
  implementation(libs.androidx.security.crypto)
  api(libs.datastore.preference)
}
