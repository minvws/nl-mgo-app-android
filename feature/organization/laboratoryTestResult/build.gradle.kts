plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.healthcareprovider.laboratoryTestResult"
}

dependencies {
    implementation(projects.data.laboratoryTestResult)
    implementation(projects.framework.environment)
    implementation(projects.component.collapsablecard)
    implementation(projects.framework.navigation)
    implementation(projects.component.results)
    implementation(projects.data.localisation)
    implementation(testFixtures(projects.data.laboratoryTestResult))
}
