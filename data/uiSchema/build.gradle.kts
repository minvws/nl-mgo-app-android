plugins {
    id("AndroidDataPlugin")
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "nl.rijksoverheid.mgo.uiSchema"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.j2v8) { artifact { type = "aar" } }
}
