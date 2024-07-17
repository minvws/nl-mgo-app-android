plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.config"
    testFixtures {
        enable = true
    }
}

dependencies {
    implementation(projects.framework.environment)
    testImplementation(testFixtures(projects.framework.environment))
}
