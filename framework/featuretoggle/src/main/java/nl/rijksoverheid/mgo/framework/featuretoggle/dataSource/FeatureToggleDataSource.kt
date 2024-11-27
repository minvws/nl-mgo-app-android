package nl.rijksoverheid.mgo.framework.featuretoggle.dataSource

import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId
import kotlinx.coroutines.flow.Flow

interface FeatureToggleDataSource {
    fun get(id: FeatureToggleId): Boolean

    fun observe(id: FeatureToggleId): Flow<Boolean>

    suspend fun set(
        id: FeatureToggleId,
        enabled: Boolean,
    )
}
