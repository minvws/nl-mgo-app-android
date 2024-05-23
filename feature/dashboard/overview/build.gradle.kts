plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.overview"
}

dependencies {
    implementation(project(":data:localisation"))
}
