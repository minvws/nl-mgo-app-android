plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.config"
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(project(":framework:environment"))
}
