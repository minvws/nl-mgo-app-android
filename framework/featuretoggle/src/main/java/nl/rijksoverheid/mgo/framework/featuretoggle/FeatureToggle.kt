package nl.rijksoverheid.mgo.framework.featuretoggle

import androidx.datastore.preferences.core.Preferences
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_FLAG_SECURE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_SKIP_PIN

sealed class FeatureToggleId {
    data object SkipPin : FeatureToggleId()

    data object FlagSecure : FeatureToggleId()

    data object AutomaticLocalisation : FeatureToggleId()
}

data class FeatureToggle(
    val id: FeatureToggleId,
    val preferenceKey: Preferences.Key<Boolean>,
    val initialValue: Boolean,
)

val featureToggles =
    listOf(
        FeatureToggle(
            id = FeatureToggleId.SkipPin,
            preferenceKey = KEY_SKIP_PIN,
            initialValue = false,
        ),
        FeatureToggle(
            id = FeatureToggleId.FlagSecure,
            preferenceKey = KEY_FLAG_SECURE,
            initialValue = true,
        ),
        FeatureToggle(
            id = FeatureToggleId.AutomaticLocalisation,
            preferenceKey = KEY_AUTOMATIC_LOCALISATION,
            initialValue = false,
        ),
    )
