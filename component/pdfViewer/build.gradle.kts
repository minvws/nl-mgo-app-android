plugins {
  id("AndroidComponentPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.component.pdfViewer"
  testFixtures {
    enable = true
  }
}

dependencies {
  implementation(projects.component.mgo)
  implementation(projects.component.theme)
  implementation(projects.framework.copy)
  implementation(libs.zoomable)
  implementation(libs.itext7.core)
}
