plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.config"
}

dependencies {
    implementation(project(":framework:environment"))
}
