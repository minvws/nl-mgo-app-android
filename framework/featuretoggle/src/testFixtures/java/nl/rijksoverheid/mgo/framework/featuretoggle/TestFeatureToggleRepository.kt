package nl.rijksoverheid.mgo.framework.featuretoggle

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository

class TestFeatureToggleRepository(
  private val allFeatureToggles: List<FeatureToggle>,
) : FeatureToggleRepository {
  private val featureToggles = mutableMapOf<FeatureToggleId, Boolean>()

  override fun getAll(): List<FeatureToggle> = allFeatureToggles

  override fun get(id: FeatureToggleId): Boolean = featureToggles[id] ?: false

  override fun observe(id: FeatureToggleId): Flow<Boolean> = flow { emit(featureToggles[id] ?: false) }

  override suspend fun set(
    toggle: FeatureToggle,
    enabled: Boolean,
  ) {
    featureToggles[toggle.id] = enabled
  }
}
