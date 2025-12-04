package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.banner

import kotlinx.coroutines.flow.Flow

interface GetHealthCategoriesBanner {
  operator fun invoke(): Flow<HealthCategoriesBannerState?>
}
