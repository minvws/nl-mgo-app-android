plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.pincode.login"
}

dependencies {
  implementation(projects.data.pincode)
  implementation(projects.component.pincode)
  testImplementation(testFixtures(projects.data.pincode))
}
