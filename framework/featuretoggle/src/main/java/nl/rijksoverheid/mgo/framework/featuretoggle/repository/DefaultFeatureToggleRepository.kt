package nl.rijksoverheid.mgo.framework.featuretoggle.repository

import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId
import nl.rijksoverheid.mgo.framework.featuretoggle.data_source.FeatureToggleLocalDataSource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/**
 * Repository that handles feature toggles. It currently only has support for a local data source, but can be expanded upon to for example
 * also support remote data sources (like firebase or an own http service).
 */
internal class DefaultFeatureToggleRepository @Inject constructor(localDataSource: FeatureToggleLocalDataSource) : FeatureToggleRepository {

    private val dataSources = listOf(localDataSource)

    override fun get(id: FeatureToggleId): Boolean {
        return dataSources.first().get(id)
    }

    override fun observe(id: FeatureToggleId): Flow<Boolean> {
        return return dataSources.first().observe(id)
    }

    override suspend fun set(id: FeatureToggleId, enabled: Boolean) {
        for (dataSource in dataSources) {
            dataSource.set(id, enabled)
        }
    }
}
