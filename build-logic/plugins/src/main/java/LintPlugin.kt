import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.KtlintPlugin

class LintPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    target.configurePlugins()
    target.configureKtLint()
  }

  private fun Project.configurePlugins() {
    apply<KtlintPlugin>()
  }

  private fun Project.configureKtLint() {
    plugins.apply {
      extensions.configure(KtlintExtension::class.java) {
        version.set("1.2.1")
      }
    }
  }
}
