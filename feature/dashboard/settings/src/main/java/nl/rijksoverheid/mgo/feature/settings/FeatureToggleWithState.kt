package nl.rijksoverheid.mgo.feature.settings

import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggle

data class FeatureToggleWithState(
    val featureToggle: FeatureToggle,
    val enabled: Boolean,
)
