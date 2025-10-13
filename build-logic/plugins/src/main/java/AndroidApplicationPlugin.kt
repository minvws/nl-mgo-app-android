import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    target.configurePlugins()
    target.addDependencies()
  }

  private fun Project.configurePlugins() {
    plugins.apply {
      apply(
        versionCatalog
          .findPlugin("androidApplication")
          .get()
          .get()
          .pluginId,
      )
      apply(
        versionCatalog
          .findPlugin("googleServices")
          .get()
          .get()
          .pluginId,
      )
      apply(
        versionCatalog
          .findPlugin("firebaseAppdistribution")
          .get()
          .get()
          .pluginId,
      )
      apply(AndroidConventionsPlugin::class.java)
      apply(AndroidUiPlugin::class.java)
      apply(LintPlugin::class.java)
      apply(LokalisePlugin::class.java)
      apply(HcimParserPlugin::class.java)
      apply(HealthCategoriesPlugin::class.java)
      apply(CiPlugin::class.java)
    }
  }

  private fun Project.addDependencies() {
    dependencies {
      // Add kover for test coverage for each module
      rootProject.subprojects.forEach { project ->
        if (project.subprojects.size == 0) {
          add("kover", project(project.path))
        }
      }
    }
  }
}
