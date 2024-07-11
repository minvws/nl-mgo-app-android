plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.onboarding"
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(project(":framework:storage"))
}
