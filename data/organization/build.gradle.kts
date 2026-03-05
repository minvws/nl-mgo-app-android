plugins {
  id("AndroidDataPlugin")
  alias(libs.plugins.sqldelight)
}

sqldelight {
  databases {
    create("OrganizationsDatabase") {
      packageName.set("nl.rijksoverheid.mgo.data.organization")
    }
  }
}

android {
  namespace = "nl.rijksoverheid.mgo.data.organization"
  testFixtures {
    enable = true
  }
}

dependencies {
  implementation(projects.framework.util)
  implementation(projects.component.organization)
  implementation(libs.sqldelight.android.driver)
  implementation(libs.sqldelight.coroutines.extensions)
  implementation(libs.sqldelight.androidx.driver)
  implementation(libs.androidx.sqlite.bundled)
  testImplementation(libs.sqldelight.sql.driver)
}
