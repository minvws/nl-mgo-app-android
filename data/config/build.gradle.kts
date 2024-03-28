plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.config"
}

dependencies {
    testImplementation(libs.okhttp.mockwebserver)
    implementation(project(":framework:environment"))
}
