plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.digid"
}

dependencies {
  implementation(projects.data.digid)
  testImplementation(testFixtures(projects.data.digid))
  testImplementation(testFixtures(projects.framework.util))
}
