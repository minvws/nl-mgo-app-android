plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.addhealthcare"
}

dependencies {
    implementation(project(":framework:environment"))
}
