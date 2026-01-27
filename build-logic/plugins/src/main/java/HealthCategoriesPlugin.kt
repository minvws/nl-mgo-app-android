import org.gradle.api.Plugin
import org.gradle.api.Project
import org.json.JSONArray
import org.json.JSONObject
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
      val destinationHealthCategoriesFileMain = File(project.rootDir, "data/healthCategories/src/main/assets/health-categories.json")
      target.moveFiles(workingDir, destinationHealthCategoriesFileMain)

      // Step 3: Clean up
      workingDir.deleteRecursively()

      // Step 4: Generate keep.xml
      target.generateKeepXml(healthCategoriesJsonFile = destinationHealthCategoriesFileMain)
    }
  }

  private fun Project.moveFiles(
    workingDir: File,
    destinationHealthCategoriesFileMain: File,
  ) {
    // Move version.json to correct location
    val targetVersionFile = File(workingDir, "version.json")
    println("Downloaded health categories configuration files. Version: ${targetVersionFile.readText()}")
    val destinationVersionFileMain = File(project.rootDir, "data/healthCategories/src/main/assets/version.json")
    val destinationVersionFileTestFixtures = File(project.rootDir, "data/healthCategories/src/testFixtures/resources/version.json")
    targetVersionFile.copyTo(destinationVersionFileMain, overwrite = true)
    targetVersionFile.copyTo(destinationVersionFileTestFixtures, overwrite = true)

    // Move health-categories.json to correct location
    val targetHealthCategoriesFile = File(workingDir, "health-categories.json")
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

  /**
   * Dynamically referenced string resources in `health-categories.json` are not directly
   * used in code, so R8/ProGuard considers them unused and may remove them during minification.
   *
   * This function generates a `keep.xml` file containing all string references from
   * `health-categories.json` to ensure these resources are preserved in release builds.
   */
  private fun Project.generateKeepXml(healthCategoriesJsonFile: File) {
    val jsonString = healthCategoriesJsonFile.readText()
    val json = JSONArray(jsonString)
    val stringResources = collectStringResources(json)
    val keepXmlFile = File(project.rootDir, "app/src/main/res/raw/keep.xml")
    val xmlContent =
      buildString {
        append("""<?xml version="1.0" encoding="utf-8"?>""")
        append("\n<resources xmlns:tools=\"http://schemas.android.com/tools\" tools:keep=\"")
        stringResources.forEachIndexed { index, stringResource ->
          append("@string/$stringResource")
          if (index < stringResources.size - 1) append(",")
        }
        append("\" />\n")
      }
    keepXmlFile.writeText(xmlContent)
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

private fun collectStringResources(json: Any): List<String> {
  val results = mutableListOf<String>()

  fun recurse(node: Any) {
    when (node) {
      is JSONObject -> {
        node.keys().forEach { key ->
          val value = node.get(key)
          if (key == "heading" || key == "subheading") {
            if (value is String) {
              results.add(value)
            }
          }
          // Recurse deeper
          recurse(value)
        }
      }
      is JSONArray -> {
        for (i in 0 until node.length()) {
          recurse(node.get(i))
        }
      }
    }
  }

  recurse(json)
  return results
}
