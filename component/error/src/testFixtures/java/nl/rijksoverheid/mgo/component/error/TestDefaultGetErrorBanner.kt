package nl.rijksoverheid.mgo.component.error

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestDefaultGetErrorBanner : GetErrorBanner {
  override fun invoke(): Flow<ErrorBannerState?> = flow { emit(null) }
}
