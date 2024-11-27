package nl.rijksoverheid.mgo.feature.settings

data class SettingsScreenViewState(
    val featureToggleSkipPin: FeatureToggle,
    val featureToggleFlagSecure: FeatureToggle,
    val featureToggleAutomaticLocalisation: FeatureToggle,
) {
    companion object {
        fun initialState(
            featureToggleSkipPin: FeatureToggle,
            featureToggleAutomaticLocalisation: FeatureToggle,
            featureToggleFlagSecure: FeatureToggle,
        ): SettingsScreenViewState {
            return SettingsScreenViewState(
                featureToggleSkipPin = featureToggleSkipPin,
                featureToggleAutomaticLocalisation = featureToggleAutomaticLocalisation,
                featureToggleFlagSecure = featureToggleFlagSecure,
            )
        }
    }
}
