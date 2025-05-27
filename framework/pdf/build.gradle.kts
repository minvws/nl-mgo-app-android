plugins {
  id("AndroidFrameworkPlugin")
  id("AndroidUiPlugin")
}

android {
  namespace = "nl.rijksoverheid.mgo.framework.pdf"
  testFixtures {
    enable = true
  }
}

dependencies {
  implementation("com.itextpdf:itext7-core:9.2.0")
}
