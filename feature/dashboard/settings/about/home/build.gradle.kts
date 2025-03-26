plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.settings.about"
}

dependencies {
    implementation(projects.data.fhirParser)
    testImplementation(testFixtures(projects.data.fhirParser))
}
