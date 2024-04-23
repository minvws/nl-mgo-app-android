plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.localisation"
}

dependencies {
    implementation(project(":framework:environment"))
    implementation(project(":framework:storage"))
}
