plugins {
  id("AndroidFeaturePlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.feature.dashboard.pdfViewer"
}

dependencies {
  implementation(libs.zoomable)
  implementation(projects.framework.pdf)
}
