package nl.rijksoverheid.mgo.component.error

import kotlinx.coroutines.flow.Flow

interface GetErrorBanner {
  operator fun invoke(): Flow<ErrorBannerState?>
}
