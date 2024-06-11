plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.concern"
}

dependencies {
    implementation(projects.data.api.dva)
    implementation(projects.framework.fhirExtension)
}
