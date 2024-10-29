plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.organizations"
}

dependencies {
    implementation(projects.data.localisation)
    testImplementation(testFixtures(projects.data.localisation))
}
