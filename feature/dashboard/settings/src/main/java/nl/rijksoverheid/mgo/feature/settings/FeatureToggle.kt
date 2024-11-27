package nl.rijksoverheid.mgo.feature.settings

import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId

data class FeatureToggle(
    val id: FeatureToggleId,
    val enabled: Boolean,
)
