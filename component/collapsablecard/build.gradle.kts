plugins {
    id("AndroidComponentPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.component.collapsablecard"
}

dependencies {
    implementation(projects.component.theme)
    implementation(projects.framework.copy)
}
