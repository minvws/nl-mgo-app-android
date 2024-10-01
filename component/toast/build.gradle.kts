plugins {
    id("AndroidComponentPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.component.toast"
}

dependencies {
    implementation(projects.component.theme)
    implementation(projects.framework.copy)
}
