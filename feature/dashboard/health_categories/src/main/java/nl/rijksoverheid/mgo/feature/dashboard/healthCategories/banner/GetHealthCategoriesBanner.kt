package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.banner

import kotlinx.coroutines.flow.Flow
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesBanner

interface GetHealthCategoriesBanner {
  operator fun invoke(): Flow<HealthCategoriesBanner>
}
