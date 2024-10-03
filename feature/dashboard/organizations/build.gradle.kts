plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.organizations"
}

dependencies {
    implementation(projects.data.localisation)
    implementation(projects.component.snackbar)
    testImplementation(testFixtures(projects.data.localisation))
}
