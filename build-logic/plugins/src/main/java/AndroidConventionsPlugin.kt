import com.android.build.gradle.BaseExtension
import nl.rijksoverheid.mgo.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

class AndroidConventionsPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configurePlugins()
        target.configureAndroid()
    }

    private fun Project.configurePlugins() {
        plugins.apply {
            apply(libs.findPlugin("kotlinAndroid").get().get().pluginId)
        }
    }

    private fun Project.configureAndroid() {
        plugins.apply {
            val minSdkVersion = libs.findVersion("android.sdk.min").get()
            val targetSdkVersion = libs.findVersion("android.sdk.target").get()
            val compileSdkVersion = libs.findVersion("android.sdk.compile").get()
            val androidExtension = extensions.getByType<BaseExtension>()
            androidExtension.apply {
                setCompileSdkVersion(compileSdkVersion.requiredVersion.toInt())
                defaultConfig.apply {
                    minSdk = minSdkVersion.requiredVersion.toInt()
                    setTargetSdkVersion(targetSdkVersion.requiredVersion.toInt())
                }
                compileOptions.apply {
                    setSourceCompatibility(JAVA_LANGUAGE_VERSION.toString())
                    setTargetCompatibility(JAVA_LANGUAGE_VERSION.toString())
                }
                buildFeatures.apply {
                    compose = true
                }
                @Suppress("UnstableApiUsage")
                composeOptions {
                    kotlinCompilerExtensionVersion = libs.findVersion("compose.compiler").get().requiredVersion
                }
                val kotlinExtension = extensions.getByType<KotlinProjectExtension>()
                kotlinExtension.jvmToolchain(JAVA_LANGUAGE_VERSION.asInt())
            }
        }
    }
}
