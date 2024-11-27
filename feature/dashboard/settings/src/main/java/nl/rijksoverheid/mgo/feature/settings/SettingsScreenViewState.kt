package nl.rijksoverheid.mgo.feature.settings

data class SettingsScreenViewState(
    val featureToggleFlagSecure: FeatureToggle,
) {
    companion object {
        fun initialState(featureToggleFlagSecure: FeatureToggle): SettingsScreenViewState {
            return SettingsScreenViewState(featureToggleFlagSecure = featureToggleFlagSecure)
        }
    }
}
