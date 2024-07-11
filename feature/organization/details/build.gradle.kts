plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.healthcareprovider.details"
}

dependencies {
    implementation(projects.data.localisation)
}
