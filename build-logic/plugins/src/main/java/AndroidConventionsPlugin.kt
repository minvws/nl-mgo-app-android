import nl.rijksoverheid.mgo.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidConventionsPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configurePlugins()
    }

    private fun Project.configurePlugins() {
        plugins.apply {
            apply(libs.findPlugin("kotlinAndroid").get().get().pluginId)
        }
    }
}
