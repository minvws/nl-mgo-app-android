plugins {
    id("AndroidFrameworkPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.framework.storage"
    testFixtures {
        enable = true
    }

    defaultConfig {
        testInstrumentationRunner = "nl.rijksoverheid.mgo.framework.test.HiltTestRunner"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(projects.framework.test) // Needed because we need a reference to the HiltTestRunner inside this gradle
    implementation(libs.androidx.security.crypto)
    api(libs.datastore.preference)
}
