plugins {
  id("AndroidComponentPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.component.error"
  testFixtures {
    enable = true
  }
}

dependencies {
  implementation(projects.component.fhir)
  implementation(projects.data.fhir)
  implementation(projects.data.healthCategories)
  implementation(projects.data.localisation)
  implementation(projects.component.mgo)
  implementation(projects.component.organization)
  implementation(projects.component.theme)
  implementation(projects.framework.copy)
  testImplementation(testFixtures(projects.data.healthCategories))
  testImplementation(testFixtures(projects.framework.storage))
  testImplementation(testFixtures(projects.data.fhir))
  testFixturesImplementation(libs.compose.runtime)
  testFixturesImplementation(projects.data.healthCategories)
  testFixturesImplementation(projects.component.organization)
}
