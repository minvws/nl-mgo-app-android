plugins {
  id("AndroidFeaturePlugin")
  alias(libs.plugins.aboutLibraries)
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.settings.about.opensource"
}

dependencies {
  implementation(libs.aboutlibraries.compose)
}
