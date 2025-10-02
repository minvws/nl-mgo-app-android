plugins {
  id("AndroidDataPlugin")
  alias(libs.plugins.serializable)
}

android {
  namespace = "nl.rijksoverheid.mgo.data.hcimParser"
  testFixtures {
    enable = true
  }
}

dependencies {
  api("wang.harlon.quickjs:wrapper-android:3.2.3")
  testImplementation("wang.harlon.quickjs:wrapper-java:3.2.3")
}
