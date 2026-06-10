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
  api(libs.okhttp)
  implementation(libs.okhttp.logging.interceptor)
  debugImplementation(libs.chucker)
  releaseImplementation(libs.chucker.no.op)
}
