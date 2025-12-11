package nl.rijksoverheid.mgo.component.theme.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

/**
 * The key used in shared preferences to store the selected app theme.
 */
const val KEY_APP_THEME = "KEY_APP_THEME"

/**
 * Represents the theme the app is currently in.
 */
enum class AppTheme {
  /**
   * Matches the system theme.
   */
  SYSTEM,

  /**
   * Forces light theme.
   */
  LIGHT,

  /**
   * Forces dark theme.
   */
  DARK,
}

/**
 * Checks if the app has a dark theme set. Always use this instead of [isSystemInDarkTheme].
 */
@Composable
fun AppTheme.isDarkTheme(): Boolean =
  when (this) {
    AppTheme.SYSTEM -> isSystemInDarkTheme()
    AppTheme.LIGHT -> false
    AppTheme.DARK -> true
  }

/**
 * The app theme is stored as a string in the local storage. This function gets the [AppTheme] from that stored string.
 *
 * @param appThemeString The string of the [AppTheme] enum value.
 * @return The selected app theme.
 */
fun getAppTheme(appThemeString: String?): AppTheme {
  if (appThemeString == null) return AppTheme.SYSTEM
  return AppTheme.valueOf(appThemeString)
}
