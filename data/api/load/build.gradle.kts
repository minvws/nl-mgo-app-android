plugins {
    id("AndroidDataPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.data.api.load"
}

dependencies {
    implementation(projects.framework.environment)
}
