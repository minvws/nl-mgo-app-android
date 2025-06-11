package nl.rijksoverheid.mgo.framework.featuretoggle.repository

import android.os.Build
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
import javax.inject.Named

/**
 * Handles various feature toggle actions. It currently only has support for a local data source, but can be expanded upon to for example
 * also support remote data sources (like firebase or an own http service).
 */
internal class DefaultFeatureToggleRepository
  @Inject
  constructor(
    @Named("sdkVersion") private val sdkVersion: Int,
    private val environmentRepository: EnvironmentRepository,
    localDataSource: FeatureToggleLocalDataSource,
  ) : FeatureToggleRepository {
    private val dataSources = listOf(localDataSource)

    /**
     * @return Get a list of all feature toggles.
     */
    override fun getAll(): List<FeatureToggle> {
      val environment = environmentRepository.getEnvironment()
      return listOf(
        flagSkipPinFeatureToggle,
        // We show a dialog if a screenshot is taken. If this functionality is not available, we disable screenshots all together. Only do this
        // in the production app for development and QA purposes.
        flagSecureFeatureToggle(sdkVersion < Build.VERSION_CODES.UPSIDE_DOWN_CAKE && environment is Environment.Prod),
        flagAutomaticLocalisationFeatureToggle(environment is Environment.Demo),
      )
    }

    /**
     * Checks if a feature toggle is enabled.
     *
     * @param id The id of the feature toggle.
     * @return True if the feature toggle is enabled.
     */
    override fun get(id: FeatureToggleId): Boolean = dataSources.first().get(id)

    /**
     * Flow that has a value if the feature toggle is enabled. Is updated whenever the state changes.
     *
     * @param id The id of the feature toggle.
     * @return True in the flow if the feature toggle is enabled.
     */
    override fun observe(id: FeatureToggleId): Flow<Boolean> = dataSources.first().observe(id)

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
