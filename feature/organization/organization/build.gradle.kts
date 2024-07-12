plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.organization.organization"
}

dependencies {
    implementation(projects.data.localisation)
}
