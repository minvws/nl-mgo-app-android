plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.pincode.create"
}

dependencies {
  implementation(projects.component.pincode)
  implementation(projects.data.pincode)
  testImplementation(testFixtures(projects.data.pincode))
}
