plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.localisation.organizationList"
}

dependencies {
  implementation(projects.data.localisation)
  implementation(projects.framework.environment)
  implementation(projects.data.healthCategories)
  implementation(projects.component.organization)
  testImplementation(projects.framework.storage)
  testImplementation(testFixtures((projects.data.localisation)))
  testImplementation(testFixtures((projects.framework.environment)))
  testImplementation(testFixtures(projects.data.healthCategories))
}
