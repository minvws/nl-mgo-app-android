package nl.rijksoverheid.mgo.data.healthData.configuration

import nl.rijksoverheid.mgo.data.healthData.configuration.models.DataSetConfig
import nl.rijksoverheid.mgo.data.healthData.configuration.models.HealthCategoryGroupConfig

/**
 * Repository for accessing health data configuration.
 *
 * Provides configuration objects that define how health data should be grouped
 * and from which data sets (endpoints) it can be retrieved.
 */
interface HealthDataConfigurationRepository {
  /**
   * Returns the available category group configurations.
   *
   * A [HealthCategoryGroupConfig] describes how health categories
   * are grouped and organized within the application.
   */
  fun getGroups(): List<HealthCategoryGroupConfig>

  /**
   * Returns the available data set configurations.
   *
   * A [DataSetConfig] defines which endpoints to query for health data
   * and how they are structured.
   */
  fun getDataSets(): List<DataSetConfig>
}
