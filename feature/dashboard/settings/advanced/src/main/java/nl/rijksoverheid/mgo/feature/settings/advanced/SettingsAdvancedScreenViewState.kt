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
)
