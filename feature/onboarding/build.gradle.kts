plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.onboarding"
}

dependencies {
    implementation(project(":data:onboarding"))
}
