plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.addhealthcare"
}

dependencies {
    implementation(project(":data:addhealthcare"))
}
