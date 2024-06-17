plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.healthcareprovider.medication"
}

dependencies {
    implementation(projects.data.medication)
    implementation(projects.framework.environment)
    implementation(projects.framework.navigation)
    implementation(projects.component.results)
    implementation(testFixtures(projects.data.medication))
}
