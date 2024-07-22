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
    testImplementation(testFixtures(projects.framework.test))
}
