package nl.rijksoverheid.mgo.data.healthCategories

import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup

interface GetHealthCategoriesFromDisk {
  operator fun invoke(): List<HealthCategoryGroup>
}
