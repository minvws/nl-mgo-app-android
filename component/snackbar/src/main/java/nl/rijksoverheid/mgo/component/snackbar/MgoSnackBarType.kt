package nl.rijksoverheid.mgo.component.snackbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
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
        MgoSnackBarType.SUCCESS -> MaterialTheme.colors.notificationSuccess()
        MgoSnackBarType.ERROR -> MaterialTheme.colors.notificationError()
        MgoSnackBarType.WARNING -> MaterialTheme.colors.notificationWarning()
        MgoSnackBarType.INFO -> MaterialTheme.colors.notificationInformation()
    }
}

@Composable
fun MgoSnackBarType.getContentColor(): Color {
    return when (this) {
        MgoSnackBarType.SUCCESS -> MaterialTheme.colors.contentPrimary(isSystemDarkTheme = !isSystemInDarkTheme())
        MgoSnackBarType.ERROR -> MaterialTheme.colors.contentPrimary(isSystemDarkTheme = !isSystemInDarkTheme())
        MgoSnackBarType.WARNING -> MaterialTheme.colors.contentPrimary(isSystemDarkTheme = false)
        MgoSnackBarType.INFO -> MaterialTheme.colors.contentPrimary(isSystemDarkTheme = !isSystemInDarkTheme())
    }
}
