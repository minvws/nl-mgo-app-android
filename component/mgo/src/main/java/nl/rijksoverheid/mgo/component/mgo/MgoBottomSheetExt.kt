package nl.rijksoverheid.mgo.component.mgo

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.view.WindowCompat
import nl.rijksoverheid.mgo.component.theme.theme.LocalAppThemeProvider
import nl.rijksoverheid.mgo.component.theme.theme.isDarkTheme

/**
 * Since we have a override in the app where you can select if you want to view the app in dark or light mode,
 * we also need to adjust the status bar color in a bottom sheet ourselves. If we don't do that it will just follow the system settings.
 */
@Composable
fun SetCorrectStatusBarIconColor() {
  val view = LocalView.current
  (view.parent as? DialogWindowProvider)?.window?.let { window ->
    WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !LocalAppThemeProvider.current.appTheme.isDarkTheme()
  }
}
