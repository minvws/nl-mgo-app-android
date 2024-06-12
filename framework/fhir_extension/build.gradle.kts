plugins {
    id("AndroidFrameworkPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.framework.fhirextension"
}

dependencies {
    implementation(libs.fhir.stdu3)
}
