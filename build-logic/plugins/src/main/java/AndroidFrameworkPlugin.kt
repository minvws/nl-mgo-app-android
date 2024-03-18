import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidFrameworkPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configurePlugins()
    }

    private fun Project.configurePlugins() {
        plugins.apply {
            apply(versionCatalog.findPlugin("androidLibrary").get().get().pluginId)
            apply(AndroidConventionsPlugin::class.java)
            apply(LintPlugin::class.java)
        }
    }
}
