package nl.rijksoverheid.mgo.component.theme.snackbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import nl.rijksoverheid.mgo.component.theme.R
import nl.rijksoverheid.mgo.component.theme.contentPrimary
import nl.rijksoverheid.mgo.component.theme.notificationError
import nl.rijksoverheid.mgo.component.theme.notificationInformation
import nl.rijksoverheid.mgo.component.theme.notificationSuccess
import nl.rijksoverheid.mgo.component.theme.notificationWarning

enum class MgoSnackBarType {
    SUCCESS,
    ERROR,
    WARNING,
    INFO,
}

@DrawableRes
fun MgoSnackBarType.getIcon(): MgoSnackBarDataIcon {
    return when (this) {
        MgoSnackBarType.SUCCESS -> R.drawable.ic_snackbar_success
        MgoSnackBarType.ERROR -> R.drawable.ic_snackbar_error
        MgoSnackBarType.WARNING -> R.drawable.ic_snackbar_warning
        MgoSnackBarType.INFO -> R.drawable.ic_snackbar_info
    }
}

@Composable
fun MgoSnackBarType.getBackgroundColor(): Color {
    return when (this) {
        MgoSnackBarType.SUCCESS -> MaterialTheme.colorScheme.notificationSuccess()
        MgoSnackBarType.ERROR -> MaterialTheme.colorScheme.notificationError()
        MgoSnackBarType.WARNING -> MaterialTheme.colorScheme.notificationWarning()
        MgoSnackBarType.INFO -> MaterialTheme.colorScheme.notificationInformation()
    }
}

@Composable
fun MgoSnackBarType.getContentColor(): Color {
    return when (this) {
        MgoSnackBarType.SUCCESS -> MaterialTheme.colorScheme.contentPrimary(isSystemDarkTheme = !isSystemInDarkTheme())
        MgoSnackBarType.ERROR -> MaterialTheme.colorScheme.contentPrimary(isSystemDarkTheme = !isSystemInDarkTheme())
        MgoSnackBarType.WARNING -> MaterialTheme.colorScheme.contentPrimary(isSystemDarkTheme = false)
        MgoSnackBarType.INFO -> MaterialTheme.colorScheme.contentPrimary(isSystemDarkTheme = !isSystemInDarkTheme())
    }
}
