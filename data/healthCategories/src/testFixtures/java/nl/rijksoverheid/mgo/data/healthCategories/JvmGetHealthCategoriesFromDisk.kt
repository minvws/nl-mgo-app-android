package nl.rijksoverheid.mgo.data.healthCategories

import kotlinx.serialization.json.Json
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup

class JvmGetHealthCategoriesFromDisk : GetHealthCategoriesFromDisk {
  private val json = Json { ignoreUnknownKeys = true }

  override fun invoke(): List<HealthCategoryGroup> {
    val jsonFile =
      this::class.java.classLoader
        ?.getResource("health-categories.json")!!
        .readText(Charsets.UTF_8)
    return json.decodeFromString(jsonFile)
  }
}
