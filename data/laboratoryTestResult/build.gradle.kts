plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.laboratoryTestResult"
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(projects.data.api.dva)
    implementation(projects.framework.fhirExtension)
}
