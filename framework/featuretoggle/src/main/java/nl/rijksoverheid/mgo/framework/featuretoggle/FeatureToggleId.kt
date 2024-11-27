package nl.rijksoverheid.mgo.framework.featuretoggle

sealed class FeatureToggleId {
    data object FlagSecureEnabled : FeatureToggleId()
}
