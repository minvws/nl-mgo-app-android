plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.search"
}

dependencies {
    testImplementation(libs.okhttp.mockwebserver)
    implementation(project(":framework:environment"))
}
