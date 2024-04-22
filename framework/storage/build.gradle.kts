plugins {
    id("AndroidFrameworkPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.framework.storage"

    defaultConfig {
        testInstrumentationRunner = "nl.rijksoverheid.mgo.framework.test.HiltTestRunner"
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":framework:test")) // Needed because we need a reference to the HiltTestRunner inside this gradle
    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.kotlin.codegen)
    api(libs.datastore.preference)
}
