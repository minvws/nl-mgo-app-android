package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.banner

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestGetHealthCategoriesBanner : GetHealthCategoriesBanner {
  override fun invoke(): Flow<HealthCategoriesBannerState> = flow { emit(HealthCategoriesBannerState.None) }
}
