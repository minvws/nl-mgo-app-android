plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.localisation"
}

dependencies {
    implementation(projects.data.api.load)
    implementation(projects.framework.storage)
}
