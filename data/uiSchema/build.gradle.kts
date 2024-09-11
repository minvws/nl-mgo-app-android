plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.uiSchema"
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(libs.j2v8) { artifact { type = "aar" } }
    implementation(libs.jackson)
}
