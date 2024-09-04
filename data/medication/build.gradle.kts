plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.medication"
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(projects.data.api.dva)
    implementation(projects.framework.fhirExtension)
    implementation(projects.data.uiSchema)
    testFixturesImplementation(projects.data.uiSchema)
    testImplementation(testFixtures(projects.framework.test))
    testImplementation(testFixtures(projects.data.uiSchema))
}
