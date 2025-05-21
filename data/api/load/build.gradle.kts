plugins {
  id("AndroidDataPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.data.api.load"
}

dependencies {
  implementation(libs.retrofit.kotlinx.serialization.converter)
  implementation(projects.framework.environment)
}
