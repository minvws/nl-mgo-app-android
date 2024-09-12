plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.overview"
}

dependencies {
    implementation(projects.data.localisation)
    implementation(projects.data.healthcare)
    testImplementation(testFixtures((projects.data.localisation)))
    testImplementation(testFixtures(projects.data.healthcare))
    testImplementation(testFixtures(projects.data.uiSchema))
}
