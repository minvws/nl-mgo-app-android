plugins {
  id("AndroidComponentPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.component.pdfViewer"
}

dependencies {
  implementation(projects.component.theme)
  implementation(projects.framework.copy)
  implementation(projects.component.mgo)
  implementation(libs.itext7.core)
  implementation(libs.zoomable)
}
