package nl.rijksoverheid.mgo.feature.settings.advanced

import nl.rijksoverheid.mgo.framework.environment.featureToggle.FeatureToggleEntry

data class SettingsAdvancedScreenViewState(
  val featureToggles: List<FeatureToggleEntry<*>>,
)
