plugins {
    id("AndroidFrameworkPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.framework.storage"
    testFixtures {
        enable = true
    }

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":framework:test")) // Needed because we need a reference to the HiltTestRunner inside this gradle
    implementation(libs.moshi.kotlin)
    implementation(libs.androidx.security.crypto)
    ksp(libs.moshi.kotlin.codegen)
    api(libs.datastore.preference)
}
