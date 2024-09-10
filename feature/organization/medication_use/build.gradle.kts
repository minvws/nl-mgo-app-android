plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.organization.medicationUse"
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(projects.data.localisation)
    implementation(projects.data.medication)
    implementation(projects.framework.environment)
    implementation(projects.framework.navigation)
    implementation(projects.component.results)
    implementation(projects.data.uiSchema)
    testImplementation(testFixtures(projects.data.localisation))
    testImplementation(testFixtures(projects.data.uiSchema))
    testImplementation(testFixtures(projects.data.medication))
    testFixturesImplementation(testFixtures(projects.data.uiSchema))
}
