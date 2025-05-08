package nl.rijksoverheid.mgo.framework.featuretoggle.repository

import kotlinx.coroutines.flow.Flow
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId

/**
 * Handles various feature toggle actions.
 */
interface FeatureToggleRepository {
  /**
   * @return Get a list of all feature toggles.
   */
  fun getAll(): List<FeatureToggle>

  /**
   * Checks if a feature toggle is enabled.
   *
   * @param id The id of the feature toggle.
   * @return True if the feature toggle is enabled.
   */
  fun get(id: FeatureToggleId): Boolean

  /**
   * Flow that has a value if the feature toggle is enabled. Is updated whenever the state changes.
   *
   * @param id The id of the feature toggle.
   * @return True in the flow if the feature toggle is enabled.
   */
  fun observe(id: FeatureToggleId): Flow<Boolean>

  /**
   * Update a feature toggle.
   *
   * @param toggle The feature toggle to update.
   * @param enabled True if the feature toggle is enabled.
   */
  suspend fun set(
    toggle: FeatureToggle,
    enabled: Boolean,
  )
}
