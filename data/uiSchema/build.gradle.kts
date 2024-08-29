plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.uiSchema"
}

dependencies {
    implementation(libs.j2v8) { artifact { type = "aar" } }
    implementation(libs.jackson)
}
