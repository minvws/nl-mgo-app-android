plugins {
  id("AndroidDataPlugin")
  alias(libs.plugins.serializable)
}

android {
  namespace = "nl.rijksoverheid.mgo.data.localisation"
  testFixtures {
    enable = true
  }
}

dependencies {
  testFixturesImplementation(libs.kotlin.stdlib)
  testImplementation(testFixtures(projects.framework.storage))
  implementation(projects.framework.storage)
  implementation(projects.component.organization)
}
