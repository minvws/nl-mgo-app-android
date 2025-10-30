plugins {
  id("AndroidComponentPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.component.uiSchema"
}

dependencies {
  implementation(projects.data.hcimParser)
  implementation(projects.data.fhir)
  implementation(projects.component.theme)
  implementation(projects.framework.copy)
  implementation(projects.data.pft)
}
