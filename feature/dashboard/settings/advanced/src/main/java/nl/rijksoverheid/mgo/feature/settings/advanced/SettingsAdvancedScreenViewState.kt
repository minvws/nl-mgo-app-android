package nl.rijksoverheid.mgo.feature.settings.advanced

/**
 * The view state for [SettingsAdvancedScreen].
 */
data class SettingsAdvancedScreenViewState(
  /**
   * True if automatic localisation is enabled.
   */
  val automaticLocalisation: Boolean,
  /**
   * True if taking screenshots of the app is enabled.
   */
  val flagSecure: Boolean,
  /**
   * True if the pin code should be skipped when launching the app.
   */
  val skipPinCode: Boolean,
)
