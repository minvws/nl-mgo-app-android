package nl.rijksoverheid.mgo.framework.featuretoggle

import androidx.datastore.preferences.core.Preferences
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_FLAG_SECURE

/**
 * Represents the feature toggles that can be used.
 */
sealed class FeatureToggleId {
  /**
   * If true, the use can not take screenshots.
   */
  data object FlagSecure : FeatureToggleId()
}

/**
 * Represents a feature toggle.
 *
 * @param id The [FeatureToggleId].
 * @param preferenceKey Key used to store this value in [Preferences].
 * @param initialValue The initial value of this feature toggle, before it has been changed.
 */
data class FeatureToggle(
  val id: FeatureToggleId,
  val preferenceKey: Preferences.Key<Boolean>,
  val initialValue: Boolean,
)

/**
 * If true, the use can not take screenshots.
 */
fun flagSecureFeatureToggle(initialValue: Boolean) =
  FeatureToggle(
    id = FeatureToggleId.FlagSecure,
    preferenceKey = KEY_FLAG_SECURE,
    initialValue = initialValue,
  )
