package nl.rijksoverheid.mgo.framework.featuretoggle

import androidx.datastore.preferences.core.Preferences
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_FLAG_SECURE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_SKIP_PIN

/**
 * Represents the feature toggles that can be used.
 */
sealed class FeatureToggleId {
  /**
   * If true, the login pin code screen can be skipped.
   */
  data object SkipPin : FeatureToggleId()

  /**
   * If true, the use can not take screenshots.
   */
  data object FlagSecure : FeatureToggleId()

  /**
   * If true, the automatic localisation flow is shown instead of the manual one.
   */
  data object AutomaticLocalisation : FeatureToggleId()
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
 * If true, the login pin code screen can be skipped.
 */
val flagSkipPinFeatureToggle =
  FeatureToggle(
    id = FeatureToggleId.SkipPin,
    preferenceKey = KEY_SKIP_PIN,
    initialValue = false,
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

/**
 * If true, the automatic localisation flow is shown instead of the manual one.
 */
fun flagAutomaticLocalisationFeatureToggle(initialValue: Boolean) =
  FeatureToggle(
    id = FeatureToggleId.AutomaticLocalisation,
    preferenceKey = KEY_AUTOMATIC_LOCALISATION,
    initialValue = initialValue,
  )
