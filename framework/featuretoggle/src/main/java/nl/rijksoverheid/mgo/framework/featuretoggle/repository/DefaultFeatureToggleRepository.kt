package nl.rijksoverheid.mgo.framework.featuretoggle.repository

import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId
import nl.rijksoverheid.mgo.framework.featuretoggle.dataSource.FeatureToggleLocalDataSource
import nl.rijksoverheid.mgo.framework.featuretoggle.flagAutomaticLocalisationFeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.flagSecureFeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.flagSkipPinFeatureToggle
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

/**
 * Repository that handles feature toggles. It currently only has support for a local data source, but can be expanded upon to for example
 * also support remote data sources (like firebase or an own http service).
 */
internal class DefaultFeatureToggleRepository @Inject constructor(
    private val environmentRepository: EnvironmentRepository,
    localDataSource: FeatureToggleLocalDataSource,
) :
    FeatureToggleRepository {
    private val dataSources = listOf(localDataSource)

    override fun getAll(): List<FeatureToggle> {
        return listOf(
            flagSkipPinFeatureToggle,
            flagSecureFeatureToggle,
            flagAutomaticLocalisationFeatureToggle(environmentRepository.getEnvironment() is Environment.Demo),
        )
    }

    override fun get(id: FeatureToggleId): Boolean {
        return dataSources.first().get(id)
    }

    override fun observe(id: FeatureToggleId): Flow<Boolean> {
        return return dataSources.first().observe(id)
    }

    override suspend fun set(
        toggle: FeatureToggle,
        enabled: Boolean,
    ) {
        for (dataSource in dataSources) {
            dataSource.set(toggle, enabled)
        }
    }
}
