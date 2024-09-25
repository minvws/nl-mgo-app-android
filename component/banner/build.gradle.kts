plugins {
    id("AndroidComponentPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.component.banner"
}

dependencies {
    implementation(projects.component.theme)
    implementation(projects.framework.copy)
}
