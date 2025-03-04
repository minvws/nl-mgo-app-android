package nl.rijksoverheid.mgo.feature.settings

import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggle

/**
 * Wrapped class for [FeatureToggle] that also holds the state if the toggle is enabled.
 *
 * @param featureToggle The [FeatureToggle].
 * @param enabled True if the [featureToggle] is enabled.
 */
data class FeatureToggleWithState(
    val featureToggle: FeatureToggle,
    val enabled: Boolean,
)
