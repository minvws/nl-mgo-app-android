plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.localisation"
    testFixtures {
        enable = true
    }
}

dependencies {
    testFixturesImplementation(libs.kotlin.stdlib)
    implementation(projects.data.api.load)
    implementation(projects.framework.storage)
}
