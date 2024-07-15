plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.organization.removeOrganization"
}

dependencies {
    implementation(projects.data.localisation)
    testImplementation(testFixtures((projects.data.localisation)))
}
