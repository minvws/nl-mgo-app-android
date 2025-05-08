plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.onboarding.introduction"
}

dependencies {
  implementation(project(":data:onboarding"))
}
