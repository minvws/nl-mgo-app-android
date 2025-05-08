package nl.rijksoverheid.mgo.feature.settings.home

import nl.rijksoverheid.mgo.component.theme.theme.AppTheme

/**
 * The view state for [SettingsHomeScreen].
 */
data class SettingsHomeScreenViewState(
  /**
   * The current app theme that is selected for the app.
   */
  val appTheme: AppTheme,
  /**
   * True if the device supports biometric login.
   */
  val deviceHasBiometric: Boolean,
  /**
   * True if the app is currently running in debug mode.
   */
  val isDebug: Boolean,
)
