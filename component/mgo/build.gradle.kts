plugins {
  id("AndroidComponentPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.component.mgo"
}
dependencies {
  implementation(projects.framework.copy)
  implementation(projects.component.theme)
  implementation(libs.compose.material3)
  implementation(libs.compose.navigation)
  implementation(projects.framework.environment)
  testImplementation(libs.androidx.ui.test.junit4.android)
  testImplementation(testFixtures(projects.framework.environment))
}
