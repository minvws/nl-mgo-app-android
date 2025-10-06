package nl.rijksoverheid.mgo.data.healthCategories

import nl.rijksoverheid.mgo.data.healthCategories.models.DataSet
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup

interface HealthCategoriesRepository {
  fun getGroups(): List<HealthCategoryGroup>

  fun getDataSets(): List<DataSet>
}
