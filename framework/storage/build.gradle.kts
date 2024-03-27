plugins {
    id("AndroidFrameworkPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.framework.storage"

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.datastore.preference)
}
