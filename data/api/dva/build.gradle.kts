plugins {
  id("AndroidDataPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.data.api.dva"
}

dependencies {
  implementation(projects.framework.environment)
  implementation(libs.retrofit.kotlinx.serialization.converter)
}
