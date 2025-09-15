import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption

class CategoriesPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    target.tasks.register("updateCategories") {
      // Files are downloaded to this directory
      val workingDir = File(target.rootDir, "categoriesTmp")
      workingDir.mkdir()

      // Step 1: Download Files
      downloadGithubArtifact(workflowId = "187469215", workingDir = workingDir)

      // Step 2: Move files
      target.moveFiles(workingDir)

      // Cleanup
      workingDir.deleteRecursively()
    }
  }

  private fun Project.moveFiles(workingDir: File) {
    // Move version.json to correct location
    val targetVersionFile = File(workingDir, "version.json")
    println("Downloaded categories. Version: ${targetVersionFile.readText()}")
    val destinationVersionFile = File(project.rootDir, "data/categories/src/main/assets/version.json")
    targetVersionFile.renameTo(destinationVersionFile)

    // Move health-categories.json to correct location
    val targetHealthCategoriesFile = File(workingDir, "health-categories.json")
    val destinationHealthCategoriesFile = File(project.rootDir, "data/categories/src/main/assets/health-categories.json")
    targetHealthCategoriesFile.renameTo(destinationHealthCategoriesFile)

    // Move data-services folder to correct location
    val targetDataServicesFile = File(workingDir, "data-services")
    val destinationDataServicesFile = File(project.rootDir, "data/categories/src/main/assets/data-services")
    targetDataServicesFile.renameTo(destinationDataServicesFile)
  }
}
