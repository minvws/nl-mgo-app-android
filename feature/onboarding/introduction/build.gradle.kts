plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.onboarding.introduction"
}

dependencies {
  implementation(libs.lottie.compose)
  implementation(project(":data:onboarding"))
}
