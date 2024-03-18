import nl.rijksoverheid.mgo.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configurePlugins()
        target.configureDependencies()
    }

    private fun Project.configurePlugins() {
        plugins.apply {
            apply(libs.findPlugin("androidApplication").get().get().pluginId)
            apply(libs.findPlugin("googleServices").get().get().pluginId)
            apply(libs.findPlugin("firebaseAppdistribution").get().get().pluginId)
            apply(AndroidConventionsPlugin::class.java)
            apply(LintPlugin::class.java)
        }
    }

    private fun Project.configureDependencies() {
        plugins.apply {
        }
    }
}
