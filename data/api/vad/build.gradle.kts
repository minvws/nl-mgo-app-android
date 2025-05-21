plugins {
  id("AndroidDataPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.data.api.vad"
}

dependencies {
  implementation(libs.retrofit.kotlinx.serialization.converter)
  implementation(projects.framework.environment)
}
