plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.digid"
}

dependencies {
    implementation(projects.data.digid)
}
