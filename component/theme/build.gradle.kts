plugins {
  id("AndroidComponentPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.component.theme"

  buildFeatures {
    compose = true
  }
}

dependencies {
  implementation(libs.compose.material3)
  implementation(libs.compose.ui.tooling)
  implementation(libs.compose.ui.tooling.preview)
  implementation(projects.framework.storage)
  testImplementation(libs.compose.ui.test.junit4)
}
