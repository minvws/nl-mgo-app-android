plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.overview"
}

dependencies {
    implementation(projects.data.localisation)
    implementation(projects.data.medication)
    testImplementation(testFixtures((projects.data.localisation)))
    testImplementation(testFixtures(projects.data.laboratoryTestResult))
    testImplementation(testFixtures(projects.data.medication))
}
