package nl.rijksoverheid.mgo.framework.featuretoggle.dataSource

import kotlinx.coroutines.flow.Flow
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId

/**
 * Contains the state of feature toggles.
 */
interface FeatureToggleDataSource {
  /**
   * @param id The [FeatureToggleId] of the [FeatureToggle].
   * @return True if the [FeatureToggle] is enabled.
   */
  fun get(id: FeatureToggleId): Boolean

  /**
   * @param id The [FeatureToggleId] of the [FeatureToggle].
   * @return Emits true if the [FeatureToggle] is enabled.
   */
  fun observe(id: FeatureToggleId): Flow<Boolean>

  /**
   * Toggle the state of a feature toggle.
   *
   * @param toggle The [FeatureToggle] to change.
   * @param enabled True if the [FeatureToggle] is enabled.
   */
  suspend fun set(
    toggle: FeatureToggle,
    enabled: Boolean,
  )
}
