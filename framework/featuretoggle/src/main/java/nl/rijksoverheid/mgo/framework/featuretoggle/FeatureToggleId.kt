package nl.rijksoverheid.mgo.framework.featuretoggle

sealed class FeatureToggleId {
    data object AutomaticLocalisation : FeatureToggleId()

    data object FlagSecureEnabled : FeatureToggleId()

    data object SkipPin : FeatureToggleId()
}
