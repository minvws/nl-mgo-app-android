plugins {
  id("AndroidDataPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.data.pincode"
  testFixtures {
    enable = true
  }
}

dependencies {
  implementation(projects.framework.storage)
  implementation(libs.bcrypt)
  implementation(libs.biometric)
  testImplementation(testFixtures(projects.framework.storage))
}
