import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

class AndroidConventionsPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configurePlugins()
        target.configureAndroid()
        target.configureDependencies()
    }

    private fun Project.configurePlugins() {
        plugins.apply {
            apply(versionCatalog.findPlugin("kotlinAndroid").get().get().pluginId)
            apply(versionCatalog.findPlugin("ksp").get().get().pluginId)
            apply(versionCatalog.findPlugin("daggerHilt").get().get().pluginId)
        }
    }

    private fun Project.configureAndroid() {
        plugins.apply {
            val minSdkVersion = versionCatalog.findVersion("android.sdk.min").get()
            val targetSdkVersion = versionCatalog.findVersion("android.sdk.target").get()
            val compileSdkVersion = versionCatalog.findVersion("android.sdk.compile").get()
            val androidExtension = extensions.getByType<BaseExtension>()
            androidExtension.apply {
                setCompileSdkVersion(compileSdkVersion.requiredVersion.toInt())
                defaultConfig.apply {
                    minSdk = minSdkVersion.requiredVersion.toInt()
                    setTargetSdkVersion(targetSdkVersion.requiredVersion.toInt())
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }
                compileOptions.apply {
                    setSourceCompatibility(JAVA_LANGUAGE_VERSION.toString())
                    setTargetCompatibility(JAVA_LANGUAGE_VERSION.toString())
                }
                testOptions.apply {
                    unitTests.apply {
                        isIncludeAndroidResources = true
                    }
                }
                val kotlinExtension = extensions.getByType<KotlinProjectExtension>()
                kotlinExtension.jvmToolchain(JAVA_LANGUAGE_VERSION.asInt())
            }
        }
    }

    private fun Project.configureDependencies() {
        dependencies {
            // Add BOMs
            addBillOfMaterials("compose.bom")
            addBillOfMaterials("coroutines.bom")

            // Coroutines
            add("implementation", versionCatalog.findLibrary("coroutines.core").get())
            add("implementation", versionCatalog.findLibrary("coroutines.android").get())
            add("testImplementation", versionCatalog.findLibrary("coroutines.test").get())
            add("androidTestImplementation", versionCatalog.findLibrary("coroutines.test").get())

            // Dagger
            add("implementation", versionCatalog.findLibrary("dagger.hilt.android").get())
            add("ksp", versionCatalog.findLibrary("dagger.hilt.compiler").get())
            add("androidTestImplementation", versionCatalog.findLibrary("dagger.hilt.testing").get())
            add("kspAndroidTest", versionCatalog.findLibrary("dagger.hilt.compiler").get())

            // Testing
            add("testImplementation", versionCatalog.findLibrary("junit").get())
            add("androidTestImplementation", versionCatalog.findLibrary("junit").get())
            add("testImplementation", versionCatalog.findLibrary("turbine").get())
            add("androidTestImplementation", versionCatalog.findLibrary("androidx.test.core").get())
            add("androidTestImplementation", versionCatalog.findLibrary("androidx.junit").get())
            add("androidTestImplementation", versionCatalog.findLibrary("androidx.test.runner").get())
            add("testImplementation", versionCatalog.findLibrary("okhttp.mockwebserver").get())

            // Logging
            add("implementation", versionCatalog.findLibrary("timber").get())
        }
    }
}
