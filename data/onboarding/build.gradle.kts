plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.onboarding"
}

dependencies {
    implementation(project(":framework:storage"))
}
