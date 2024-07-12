plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.healthcareprovider.medicationUse"
}

dependencies {
    implementation(projects.data.localisation)
    implementation(projects.data.medication)
    implementation(projects.framework.environment)
    implementation(projects.framework.navigation)
    implementation(projects.component.results)
    implementation(testFixtures(projects.data.medication))
}
