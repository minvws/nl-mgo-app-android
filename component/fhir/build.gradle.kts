plugins {
  id("AndroidComponentPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.component.fhir"
}

dependencies {
  implementation(projects.data.fhir)
  implementation(projects.data.healthCategories)
  implementation(projects.component.organization)
}
