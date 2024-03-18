import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configurePlugins()
        target.configureDependencies()
    }

    private fun Project.configurePlugins() {
        plugins.apply {
            apply(versionCatalog.findPlugin("androidLibrary").get().get().pluginId)
            apply(AndroidConventionsPlugin::class.java)
            apply(AndroidUiPlugin::class.java)
            apply(LintPlugin::class.java)
        }
    }

    private fun Project.configureDependencies() {
        dependencies {
            add("implementation", project(":component:theme"))
        }
    }
}
