plugins {
    id("AndroidFeaturePlugin")
    alias(libs.plugins.serializable)
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.dashboard.healthCategory"
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(projects.data.localisation)
    implementation(projects.framework.environment)
    implementation(projects.framework.navigation)
    implementation(projects.data.uiSchema)
    implementation(projects.data.healthcare)
    implementation(projects.component.banner)
    testImplementation(testFixtures(projects.data.localisation))
    testImplementation(testFixtures(projects.data.healthcare))
    testFixturesImplementation(testFixtures(projects.data.uiSchema))
}
