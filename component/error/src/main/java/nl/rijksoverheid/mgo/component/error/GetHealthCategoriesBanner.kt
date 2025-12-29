package nl.rijksoverheid.mgo.component.error

import kotlinx.coroutines.flow.Flow

interface GetHealthCategoriesBanner {
  operator fun invoke(): Flow<HealthCategoriesBannerState?>
}
