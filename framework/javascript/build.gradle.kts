plugins {
  id("AndroidFrameworkPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.framework.javascript"
  testFixtures {
    enable = true
  }
}

dependencies {
  implementation(libs.quickjs.wrapper.android)
  testImplementation(libs.quickjs.wrapper.java)
  testFixturesImplementation(libs.quickjs.wrapper.java)
}
