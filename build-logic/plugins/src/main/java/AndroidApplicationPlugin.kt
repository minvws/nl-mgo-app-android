import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.configurePlugins()
    }

    private fun Project.configurePlugins() {
        plugins.apply {
            apply(versionCatalog.findPlugin("androidApplication").get().get().pluginId)
            apply(versionCatalog.findPlugin("googleServices").get().get().pluginId)
            apply(versionCatalog.findPlugin("firebaseAppdistribution").get().get().pluginId)
            apply(AndroidConventionsPlugin::class.java)
            apply(AndroidUiPlugin::class.java)
            apply(LintPlugin::class.java)
            apply(LokalisePlugin::class.java)
            apply(FhirParserPlugin::class.java)
            apply(RunCiPlugin::class.java)
            apply(CreatePrPlugin::class.java)
        }
    }
}
