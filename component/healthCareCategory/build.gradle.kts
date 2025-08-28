plugins {
  id("AndroidComponentPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.component.healthCareCategory"
}

dependencies {
  implementation(projects.data.healthcare)
  implementation(projects.component.theme)
  implementation(projects.framework.copy)
}
