plugins {
  id("AndroidComponentPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.component.pdfViewer"
}

dependencies {
  implementation(projects.component.mgo)
  implementation(projects.component.theme)
  implementation(projects.framework.copy)
  implementation(projects.framework.storage)
  implementation(libs.zoomable)
  implementation(libs.itext7.core)
  testImplementation(testFixtures(projects.framework.storage))
}
