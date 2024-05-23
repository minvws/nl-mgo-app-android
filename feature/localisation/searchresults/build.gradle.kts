plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.localisation.searchresults"
}

dependencies {
    implementation(project(":data:localisation"))
    implementation(project(":framework:environment"))
}
