plugins {
    id("AndroidFeaturePlugin")
}

android {
    namespace = "nl.rijksoverheid.mgo.feature.onboarding"
}

dependencies {
    implementation(project(":data:onboarding"))
    androidTestImplementation("com.android.support.test.uiautomator:uiautomator-v18:2.1.3")
}
