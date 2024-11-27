plugins {
    id("AndroidFrameworkPlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.framework.featuretoggle"
}

dependencies {
    implementation(projects.framework.storage)
}
