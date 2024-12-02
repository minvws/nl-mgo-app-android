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
    implementation(projects.framework.copy)
    implementation(projects.framework.storage)
    testImplementation(testFixtures(projects.data.localisation))
    testImplementation(testFixtures(projects.data.uiSchema))
    testImplementation(testFixtures(projects.framework.test))
    testFixturesImplementation(projects.data.localisation)
    testFixturesImplementation(projects.data.uiSchema)
}
