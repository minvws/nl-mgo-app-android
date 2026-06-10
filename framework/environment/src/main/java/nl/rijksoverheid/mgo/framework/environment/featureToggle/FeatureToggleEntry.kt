package nl.rijksoverheid.mgo.framework.environment.featureToggle

data class FeatureToggleEntry<T>(
  val toggle: FeatureToggle<T>,
  val value: T,
)
