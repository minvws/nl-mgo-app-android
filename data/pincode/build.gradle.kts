plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.pincode"
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(project(":framework:storage"))
}
