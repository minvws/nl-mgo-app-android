package nl.rijksoverheid.mgo.data.healthCategories

import kotlinx.serialization.json.Json
import nl.rijksoverheid.mgo.data.healthCategories.models.DataSet
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import java.io.File

class JvmHealthCategoriesRepository : HealthCategoriesRepository {
  private val json = Json.Default

  override fun getGroups(): List<HealthCategoryGroup> {
    val jsonFile =
      this::class.java.classLoader
        ?.getResource("health-categories.json")!!
        .readText(Charsets.UTF_8)
    return json.decodeFromString(jsonFile)
  }

  override fun getDataSets(): List<DataSet> {
    val resourceDir = "data-services"
    val dirUrl =
      this::class.java.classLoader?.getResource(resourceDir)
        ?: throw IllegalStateException("Resource folder not found: $resourceDir")

    val folder = File(dirUrl.toURI())

    val jsonFiles =
      folder
        .walkTopDown()
        .filter { it.isFile && it.extension == "json" }
        .toList()

    return jsonFiles.map { file ->
      val jsonContent = file.readText(Charsets.UTF_8)
      json.decodeFromString<DataSet>(jsonContent)
    }
  }
}
