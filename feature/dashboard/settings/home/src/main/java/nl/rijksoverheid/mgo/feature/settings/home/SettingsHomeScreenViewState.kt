package nl.rijksoverheid.mgo.feature.settings.home

/**
 * The view state for [SettingsHomeScreen].
 */
data class SettingsHomeScreenViewState(
  /**
   * True if the device supports biometric login.
   */
  val deviceHasBiometric: Boolean,
  /**
   * True if the app is currently running in debug mode.
   */
  val isDebug: Boolean,
)
