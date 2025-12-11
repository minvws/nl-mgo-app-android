package nl.rijksoverheid.mgo.data.healthCategories

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import nl.rijksoverheid.mgo.data.healthCategories.models.DataSet
import javax.inject.Inject

class AndroidGetDataSetsFromDisk
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
  ) : GetDataSetsFromDisk {
    private val json = Json { ignoreUnknownKeys = true }

    override fun invoke(): List<DataSet> {
      val assetDir = "data-services"
      val jsonFiles = context.assets.list(assetDir)!!

      return jsonFiles
        .filter { it.endsWith(".json") }
        .map { fileName ->
          val jsonContent =
            context.assets
              .open("$assetDir/$fileName")
              .bufferedReader(Charsets.UTF_8)
              .use { it.readText() }

          json.decodeFromString<DataSet>(jsonContent)
        }
    }
  }
