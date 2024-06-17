plugins {
    id("AndroidComponentPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.component.results"
}

dependencies {
    implementation(projects.component.theme)
    api(projects.component.collapsablecard)
    implementation(projects.framework.copy)
}
