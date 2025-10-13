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
   * The fhir parser version. Example:
   * { "version": "main", "git_ref": "d2c2081aefcaa7c0e8c413a1b8c654bcdcbe7705", "created": "2025-03-21T16:01:38"}
   */
  val hcimParserVersion: String,
  /**
   * A link to view more information on web about the privacy of the app.
   */
  @StringRes val privacyUrl: Int,
)
