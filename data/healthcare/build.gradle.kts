plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.healthcare"
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(projects.data.localisation)
    implementation(projects.data.uiSchema)
    implementation(projects.data.api.dva)
}
