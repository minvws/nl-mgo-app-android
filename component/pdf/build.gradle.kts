plugins {
  id("AndroidComponentPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.component.pdf"
}

dependencies {
  implementation(projects.component.theme)
  implementation(projects.framework.copy)
  implementation(projects.data.hcimParser)
  implementation(libs.itext7.core)
}
