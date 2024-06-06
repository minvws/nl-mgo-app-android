plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.medication"
}

dependencies {
    implementation(projects.data.api.dva)
}
