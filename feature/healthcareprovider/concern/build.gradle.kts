plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.healthcareprovider.concern"
}

dependencies {
    implementation(projects.data.concern)
    implementation(projects.framework.environment)
    implementation(projects.component.collapsablecard)
    implementation(projects.framework.navigation)
    implementation(projects.component.results)
    implementation(testFixtures(projects.data.concern))
}
