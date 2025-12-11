package nl.rijksoverheid.mgo.component.mgo.snackbar

import androidx.annotation.DrawableRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import nl.rijksoverheid.mgo.component.mgo.R
import nl.rijksoverheid.mgo.component.theme.LabelsPrimary
import nl.rijksoverheid.mgo.component.theme.StatesCritical
import nl.rijksoverheid.mgo.component.theme.StatesInformative
import nl.rijksoverheid.mgo.component.theme.StatesPositive
import nl.rijksoverheid.mgo.component.theme.StatesWarning
import nl.rijksoverheid.mgo.component.theme.theme.LocalAppThemeProvider
import nl.rijksoverheid.mgo.component.theme.theme.isDarkTheme

enum class MgoSnackBarType {
  SUCCESS,
  ERROR,
  WARNING,
  INFO,
}

@DrawableRes
fun MgoSnackBarType.getIcon(): MgoSnackBarDataIcon =
  when (this) {
    MgoSnackBarType.SUCCESS -> R.drawable.ic_snackbar_success
    MgoSnackBarType.ERROR -> R.drawable.ic_snackbar_error
    MgoSnackBarType.WARNING -> R.drawable.ic_snackbar_warning
    MgoSnackBarType.INFO -> R.drawable.ic_snackbar_info
  }

@Composable
fun MgoSnackBarType.getBackgroundColor(): Color =
  when (this) {
    MgoSnackBarType.SUCCESS -> MaterialTheme.colorScheme.StatesPositive()
    MgoSnackBarType.ERROR -> MaterialTheme.colorScheme.StatesCritical()
    MgoSnackBarType.WARNING -> MaterialTheme.colorScheme.StatesWarning()
    MgoSnackBarType.INFO -> MaterialTheme.colorScheme.StatesInformative()
  }

@Composable
fun MgoSnackBarType.getContentColor(): Color =
  when (this) {
    MgoSnackBarType.SUCCESS ->
      MaterialTheme.colorScheme.LabelsPrimary(
        isSystemDarkTheme =
          !LocalAppThemeProvider.current
            .appTheme
            .isDarkTheme(),
      )

    MgoSnackBarType.ERROR ->
      MaterialTheme.colorScheme.LabelsPrimary(
        isSystemDarkTheme =
          !LocalAppThemeProvider.current
            .appTheme
            .isDarkTheme(),
      )

    MgoSnackBarType.WARNING ->
      MaterialTheme.colorScheme.LabelsPrimary(
        isSystemDarkTheme = false,
      )
    MgoSnackBarType.INFO ->
      MaterialTheme.colorScheme.LabelsPrimary(
        isSystemDarkTheme =
          !LocalAppThemeProvider.current
            .appTheme
            .isDarkTheme(),
      )
  }
