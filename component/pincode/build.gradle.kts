plugins {
    id("AndroidComponentPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.component.pincode"
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(projects.component.theme)
    implementation(projects.framework.copy)
}
