plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.dashboard.removeOrganization"
}

dependencies {
  implementation(projects.component.organization)
  implementation(projects.data.organization)
  testImplementation(projects.framework.storage)
  testImplementation(testFixtures(projects.data.organization))
}
