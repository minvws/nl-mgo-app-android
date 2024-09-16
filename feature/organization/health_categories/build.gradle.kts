plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.organization.healthCategories"
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(projects.data.localisation)
    implementation(projects.framework.environment)
    implementation(projects.framework.navigation)
    implementation(projects.component.results)
    implementation(projects.data.uiSchema)
    implementation(projects.data.healthcare)
    testImplementation(testFixtures(projects.data.localisation))
    testImplementation(testFixtures(projects.data.healthcare))
    testFixturesImplementation(testFixtures(projects.data.uiSchema))
}
