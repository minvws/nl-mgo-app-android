plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.localisation.manual"
}

dependencies {
  implementation(projects.component.organization)
  implementation(projects.data.organization)
  implementation(projects.data.healthCategories)
  testImplementation(testFixtures(projects.data.healthCategories))
}
