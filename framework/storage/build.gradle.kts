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
    implementation(libs.moshi.kotlin)
    implementation(libs.androidx.security.crypto)
    ksp(libs.moshi.kotlin.codegen)
    api(libs.datastore.preference)
}
