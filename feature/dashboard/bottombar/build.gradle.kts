plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.bottombar"
}

dependencies {
  implementation(libs.kotlin.reflect)
  implementation(projects.data.localisation)
  implementation(projects.data.healthcare)
}
