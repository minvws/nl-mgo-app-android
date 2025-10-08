plugins {
  id("AndroidComponentPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.component.healthCategories"
}

dependencies {
  implementation(projects.data.healthCategories)
  implementation(projects.component.theme)
  implementation(projects.framework.copy)
}
