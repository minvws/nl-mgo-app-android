package nl.rijksoverheid.mgo.data.healthData.configuration

import nl.rijksoverheid.mgo.data.healthData.configuration.models.DataSetConfig
import nl.rijksoverheid.mgo.data.healthData.configuration.models.HealthCategoryGroupConfig
import nl.rijksoverheid.mgo.data.healthData.configuration.models.TEST_COMMON_CLINICAL_DATA_SET
import nl.rijksoverheid.mgo.data.healthData.configuration.models.TEST_GENERAL_PRACTITIONER_DATA
import nl.rijksoverheid.mgo.data.healthData.configuration.models.TEST_HEALTH_CATEGORY_GROUP_HEALTH

class TestHealthDataConfigurationRepository : HealthDataConfigurationRepository {
  override fun getGroups(): List<HealthCategoryGroupConfig> = listOf(TEST_HEALTH_CATEGORY_GROUP_HEALTH)

  override fun getDataSets(): List<DataSetConfig> = listOf(TEST_COMMON_CLINICAL_DATA_SET, TEST_GENERAL_PRACTITIONER_DATA)
}
