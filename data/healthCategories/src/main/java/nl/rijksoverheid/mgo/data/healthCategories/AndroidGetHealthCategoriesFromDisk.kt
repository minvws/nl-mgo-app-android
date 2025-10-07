package nl.rijksoverheid.mgo.data.healthCategories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import javax.inject.Inject

internal class AndroidGetHealthCategoriesFromDisk
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
  ) : GetHealthCategoriesFromDisk {
    private val json = Json.Default

    override fun invoke(): List<HealthCategoryGroup> {
      val jsonFile =
        context.assets
          .open("health-categories.json")
          .bufferedReader(Charsets.UTF_8)
          .use { it.readText() }
      return json.decodeFromString(jsonFile)
    }
  }
