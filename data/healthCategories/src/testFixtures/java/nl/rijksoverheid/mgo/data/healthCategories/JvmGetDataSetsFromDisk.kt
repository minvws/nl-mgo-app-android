package nl.rijksoverheid.mgo.data.healthCategories

import kotlinx.serialization.json.Json
import nl.rijksoverheid.mgo.data.healthCategories.models.DataSet
import java.io.File

class JvmGetDataSetsFromDisk : GetDataSetsFromDisk {
  private val json = Json { ignoreUnknownKeys = true }

  override fun invoke(): List<DataSet> {
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
