package nl.rijksoverheid.mgo.framework.featuretoggle.repository

import kotlinx.coroutines.flow.Flow
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId

interface FeatureToggleRepository {
    fun get(id: FeatureToggleId): Boolean
    fun observe(id: FeatureToggleId): Flow<Boolean>
    suspend fun set(id: FeatureToggleId, enabled: Boolean)
}
