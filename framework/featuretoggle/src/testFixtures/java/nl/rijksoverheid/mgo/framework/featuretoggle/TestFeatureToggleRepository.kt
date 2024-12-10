package nl.rijksoverheid.mgo.framework.featuretoggle

import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestFeatureToggleRepository : FeatureToggleRepository {
    private val featureToggles = mutableMapOf<FeatureToggleId, Boolean>()

    override fun get(id: FeatureToggleId): Boolean {
        return featureToggles[id] ?: false
    }

    override fun observe(id: FeatureToggleId): Flow<Boolean> {
        return flow { emit(featureToggles[id] ?: false) }
    }

    override suspend fun set(
        toggle: FeatureToggle,
        enabled: Boolean,
    ) {
        featureToggles[toggle.id] = enabled
    }
}
