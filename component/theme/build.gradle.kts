plugins {
    id("AndroidComponentPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.component.theme"
}
dependencies {
    implementation(project(":framework:environment"))
}
