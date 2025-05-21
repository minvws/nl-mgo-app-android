package nl.rijksoverheid.mgo.framework.featuretoggle.repository

import kotlinx.coroutines.flow.Flow
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId
import nl.rijksoverheid.mgo.framework.featuretoggle.dataSource.FeatureToggleLocalDataSource
import nl.rijksoverheid.mgo.framework.featuretoggle.flagAutomaticLocalisationFeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.flagSecureFeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.flagSkipPinFeatureToggle
import javax.inject.Inject

/**
 * Handles various feature toggle actions. It currently only has support for a local data source, but can be expanded upon to for example
 * also support remote data sources (like firebase or an own http service).
 */
internal class DefaultFeatureToggleRepository
  @Inject
  constructor(
    private val environmentRepository: EnvironmentRepository,
    localDataSource: FeatureToggleLocalDataSource,
  ) :
  FeatureToggleRepository {
    private val dataSources = listOf(localDataSource)

    /**
     * @return Get a list of all feature toggles.
     */
    override fun getAll(): List<FeatureToggle> {
      return listOf(
        flagSkipPinFeatureToggle,
        flagSecureFeatureToggle,
        flagAutomaticLocalisationFeatureToggle(environmentRepository.getEnvironment() is Environment.Demo),
      )
    }

    /**
     * Checks if a feature toggle is enabled.
     *
     * @param id The id of the feature toggle.
     * @return True if the feature toggle is enabled.
     */
    override fun get(id: FeatureToggleId): Boolean {
      return dataSources.first().get(id)
    }

    /**
     * Flow that has a value if the feature toggle is enabled. Is updated whenever the state changes.
     *
     * @param id The id of the feature toggle.
     * @return True in the flow if the feature toggle is enabled.
     */
    override fun observe(id: FeatureToggleId): Flow<Boolean> {
      return dataSources.first().observe(id)
    }

    /**
     * Update a feature toggle.
     *
     * @param toggle The feature toggle to update.
     * @param enabled True if the feature toggle is enabled.
     */
    override suspend fun set(
      toggle: FeatureToggle,
      enabled: Boolean,
    ) {
      for (dataSource in dataSources) {
        dataSource.set(toggle, enabled)
      }
    }
  }
