plugins {
  id("AndroidFrameworkPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.framework.network"

  buildFeatures {
    buildConfig = true
  }
}

dependencies {
  api(libs.retrofit)
  api(libs.okhttp)
  debugImplementation(libs.chucker)
  releaseImplementation(libs.chucker.no.op)
}
