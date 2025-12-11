plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.dashboard.removeOrganization"
}

dependencies {
  implementation(projects.data.localisation)
  implementation(projects.component.organization)
  testImplementation(testFixtures((projects.data.localisation)))
  testImplementation(projects.framework.storage)
}
