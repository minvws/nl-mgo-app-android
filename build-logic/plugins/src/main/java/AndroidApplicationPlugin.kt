import com.android.build.gradle.BaseExtension
import nl.rijksoverheid.mgo.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configurePlugins()
        target.configureDependencies()
        target.configureAndroid()
    }

    private fun Project.configurePlugins() {
        plugins.apply {
            apply(AndroidConventionsPlugin::class.java)
            apply(LintPlugin::class.java)
            apply(libs.findPlugin("androidApplication").get().get().pluginId)
            apply(libs.findPlugin("googleServices").get().get().pluginId)
            apply(libs.findPlugin("firebaseAppdistribution").get().get().pluginId)
        }
    }

    private fun Project.configureDependencies() {
        plugins.apply {
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
                    setSourceCompatibility(JavaVersion.VERSION_1_8.toString())
                    setTargetCompatibility(JavaVersion.VERSION_1_8.toString())
                }
            }
        }
    }
}
