package nl.rijksoverheid.mgo.data.healthData.configuration

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import nl.rijksoverheid.mgo.data.healthData.configuration.models.DataSetConfig
import nl.rijksoverheid.mgo.data.healthData.configuration.models.HealthCategoryGroupConfig
import javax.inject.Inject

/**
 * Repository for accessing health data configuration.
 *
 * Provides configuration objects that define how health data should be grouped
 * and from which data sets (endpoints) it can be retrieved.
 */
internal class DefaultHealthDataConfigurationRepository
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
  ) : HealthDataConfigurationRepository {
    private val json = Json.Default

    /**
     * Returns the available category group configurations from the assets/health-categories.json file.
     *
     * A [HealthCategoryGroupConfig] describes how health categories
     * are grouped and organized within the application.
     */
    override fun getGroups(): List<HealthCategoryGroupConfig> {
      val jsonFile =
        context.assets.open("health-categories.json").bufferedReader().use { reader ->
          reader.readText()
        }
      val healthCategories = json.decodeFromString<List<HealthCategoryGroupConfig>>(jsonFile)
      return healthCategories
    }

    /**
     * Returns the available data set configurations from the json files inside the assets/data-services folder.
     *
     * A [DataSetConfig] defines which endpoints to query for health data
     * and how they are structured.
     */
    override fun getDataSets(): List<DataSetConfig> =
      listOf(
        getDataSetConfig("data-services/48-common-clinical-dataset.json"),
        getDataSetConfig("data-services/49-general-practitioner-data.json"),
        getDataSetConfig("data-services/51-documents-pdfa.json"),
        getDataSetConfig("data-services/63-vaccination-immunization.json"),
      )

    private fun getDataSetConfig(fileName: String): DataSetConfig {
      val jsonFile =
        context.assets.open(fileName).bufferedReader().use { reader ->
          reader.readText()
        }

      return json.decodeFromString<DataSetConfig>(jsonFile)
    }
  }
