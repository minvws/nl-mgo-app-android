import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

class AndroidUiPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configurePlugins()
        target.configureDependencies()
        target.configureAndroid()
    }

    private fun Project.configurePlugins() {
        plugins.apply {
            apply(versionCatalog.findPlugin("composeCompiler").get().get().pluginId)
            apply(SnapshotsPlugin::class.java)
        }
    }

    private fun Project.configureAndroid() {
        plugins.apply {
            val androidExtension = extensions.getByType<BaseExtension>()
            androidExtension.apply {
                buildFeatures.apply {
                    compose = true
                }
                @Suppress("UnstableApiUsage")
                composeOptions {
                    kotlinCompilerExtensionVersion = versionCatalog.findVersion("compose.compiler").get().requiredVersion
                }
            }
        }
    }

    private fun Project.configureDependencies() {
        dependencies.apply {
            // Modules
            add("implementation", project(":framework:util"))

            // Android
            add("implementation", versionCatalog.findLibrary("core.ktx").get())
            add("implementation", versionCatalog.findLibrary("appcompat").get())
            add("implementation", versionCatalog.findLibrary("material").get())

            // Compose
            add("implementation", versionCatalog.findLibrary("compose.material3").get())
            add("implementation", versionCatalog.findLibrary("compose.material.icons.core").get())
            add("implementation", versionCatalog.findLibrary("compose.material.icons.extended").get())
            add("implementation", versionCatalog.findLibrary("compose.ui.tooling.preview").get())
            add("implementation", versionCatalog.findLibrary("compose.ui.tooling").get())
            add("implementation", versionCatalog.findLibrary("compose.activity").get())
            add("implementation", versionCatalog.findLibrary("compose.lifecycle").get())
            add("debugImplementation", versionCatalog.findLibrary("compose.ui.tooling").get())
            add("debugImplementation", versionCatalog.findLibrary("compose.ui.test.manifest").get())
            add("implementation", versionCatalog.findLibrary("dagger.hilt.compose.navigation").get())
        }
    }
}
