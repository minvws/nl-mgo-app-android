package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.banner

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesBanner

class TestGetHealthCategoriesBanner : GetHealthCategoriesBanner {
  override fun invoke(): Flow<HealthCategoriesBanner> = flow { emit(HealthCategoriesBanner.NONE) }
}
