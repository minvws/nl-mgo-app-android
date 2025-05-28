plugins {
  id("AndroidFrameworkPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.framework.pdf"
  testFixtures {
    enable = true
  }
}

dependencies {
  implementation(projects.framework.copy)
  implementation(libs.itext7.core)
}
