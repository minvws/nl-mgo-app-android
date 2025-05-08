plugins {
  id("AndroidComponentPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.component.pincode"
  testFixtures {
    enable = true
  }
}

dependencies {
  implementation(projects.component.theme)
  implementation(projects.component.mgo)
  implementation(projects.framework.copy)
  implementation(libs.biometric)
}
