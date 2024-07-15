plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.healthcareprovider.removeOrganization"
}

dependencies {
    implementation(projects.data.localisation)
    testImplementation(testFixtures((projects.data.localisation)))
}
