package nl.rijksoverheid.mgo.feature.settings.about.home

import androidx.annotation.StringRes

/**
 * The view state for [SettingsAboutHomeScreen].
 */
internal data class SettingsAboutHomeScreenViewState(
  /**
   * The version name of the app (e.g., 1.0.0).
   */
  val appVersionName: String,
  /**
   * The version code of the app (e.g., 1)
   */
  val appVersionCode: Int,
  /**
   * A link to view more information on web about the privacy of the app.
   */
  @StringRes val privacyUrl: Int,
)
