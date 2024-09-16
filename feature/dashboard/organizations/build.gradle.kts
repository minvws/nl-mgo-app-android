plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.organizations"
}

dependencies {
    implementation(projects.feature.dashboard.overview)
    implementation(projects.data.localisation)
    testImplementation(testFixtures(projects.data.localisation))
}
