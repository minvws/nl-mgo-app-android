plugins {
    id("AndroidComponentPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.component.snackbar"
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(projects.component.theme)
    implementation(projects.framework.copy)
    api(libs.compose.material3)
}
