plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.healthcareprovider"
}

dependencies {
    implementation(projects.framework.environment)
    api(libs.fhir.stdu3)
}
