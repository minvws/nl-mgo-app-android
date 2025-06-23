package nl.rijksoverheid.mgo.framework.featuretoggle

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import nl.rijksoverheid.mgo.framework.featuretoggle.dataSource.FeatureToggleDataSource

class TestFeatureToggleDataSource : FeatureToggleDataSource {
  override fun get(id: FeatureToggleId): Boolean = true

  override fun observe(id: FeatureToggleId): Flow<Boolean> = flowOf(true)

  override suspend fun set(
    toggle: FeatureToggle,
    enabled: Boolean,
  ) {
  }
}
