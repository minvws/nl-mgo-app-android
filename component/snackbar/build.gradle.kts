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
    api("androidx.compose.material3:material3:1.3.0")
}
