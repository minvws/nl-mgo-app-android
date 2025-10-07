import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class HealthCategoriesPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    target.tasks.register("updateHealthCategories") {
      // Files are downloaded to this directory
      val workingDir = File(target.rootDir, "healthCategoriesTmp")
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
    println("Downloaded health categories configuration files. Version: ${targetVersionFile.readText()}")
    val destinationVersionFileMain = File(project.rootDir, "data/healthCategories/src/main/assets/version.json")
    val destinationVersionFileTestFixtures = File(project.rootDir, "data/healthCategories/src/testFixtures/resources/version.json")
    targetVersionFile.copyTo(destinationVersionFileMain, overwrite = true)
    targetVersionFile.copyTo(destinationVersionFileTestFixtures, overwrite = true)

    // Move health-categories.json to correct location
    val targetHealthCategoriesFile = File(workingDir, "health-categories.json")
    val destinationHealthCategoriesFileMain = File(project.rootDir, "data/healthCategories/src/main/assets/health-categories.json")
    val destinationHealthCategoriesFileTestFixtures = File(project.rootDir, "data/healthCategories/src/testFixtures/resources/health-categories.json")
    targetHealthCategoriesFile.copyTo(destinationHealthCategoriesFileMain, overwrite = true)
    targetHealthCategoriesFile.copyTo(destinationHealthCategoriesFileTestFixtures, overwrite = true)

    // Move data-services folder to correct location
    val targetDataServicesFile = File(workingDir, "data-services")
    val destinationDataServicesFileMain = File(project.rootDir, "data/healthCategories/src/main/assets/data-services")
    val destinationDataServicesFileTest = File(project.rootDir, "data/healthCategories/src/test/assets/data-services")
    val destinationDataServicesFileTestFixtures = File(project.rootDir, "data/healthCategories/src/testFixtures/resources/data-services")
    copyDirectoryRecursively(targetDataServicesFile, destinationDataServicesFileMain)
    copyDirectoryRecursively(targetDataServicesFile, destinationDataServicesFileTest)
    copyDirectoryRecursively(targetDataServicesFile, destinationDataServicesFileTestFixtures)
  }
}

private fun copyDirectoryRecursively(
  source: File,
  destination: File,
) {
  if (!source.exists()) return

  if (source.isDirectory) {
    if (!destination.exists()) {
      destination.mkdirs()
    }
    source.listFiles()?.forEach { child ->
      copyDirectoryRecursively(child, File(destination, child.name))
    }
  } else {
    source.copyTo(destination, overwrite = true)
  }
}
