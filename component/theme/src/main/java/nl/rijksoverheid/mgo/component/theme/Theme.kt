package nl.rijksoverheid.mgo.component.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun MgoTheme(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    // Make sure the status bar is the same color as the background
    val view = LocalView.current
    val backgroundColor = MaterialTheme.colorScheme.backgroundPrimary().toArgb()
    if (!view.isInEditMode && !LocalInspectionMode.current) {
        SideEffect {
            val activity = view.context as Activity
            activity.window.statusBarColor = backgroundColor
            WindowCompat.getInsetsController(activity.window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = if (darkTheme) getDarkColorScheme() else getLightColorScheme()
    ) {
        Surface(
            modifier = modifier.safeDrawingPadding(),
            color = MaterialTheme.colorScheme.background,
            content = content,
        )
    }
}

@Composable
private fun getDarkColorScheme() =
    darkColorScheme(
        primary = MaterialTheme.colorScheme.actionPrimaryDefaultBackground(true),
        secondary = MaterialTheme.colorScheme.actionSecondaryDefaultBackground(true),
        background = MaterialTheme.colorScheme.backgroundPrimary(true),
        surface = MaterialTheme.colorScheme.backgroundSecondary(true),
        onPrimary = MaterialTheme.colorScheme.backgroundSecondary(false),
        onSecondary = MaterialTheme.colorScheme.actionSecondaryDefaultText(true),
        onBackground = MaterialTheme.colorScheme.contentPrimary(true),
        onSurface = MaterialTheme.colorScheme.contentPrimary(true),
        error = MaterialTheme.colorScheme.notificationError(true),
    )

@Composable
private fun getLightColorScheme() =
    lightColorScheme(
        primary = MaterialTheme.colorScheme.actionPrimaryDefaultBackground(false),
        secondary = MaterialTheme.colorScheme.actionSecondaryDefaultBackground(false),
        background = MaterialTheme.colorScheme.backgroundPrimary(false),
        surface = MaterialTheme.colorScheme.backgroundSecondary(false),
        onPrimary = MaterialTheme.colorScheme.backgroundSecondary(false),
        onSecondary = MaterialTheme.colorScheme.actionSecondaryDefaultText(false),
        onBackground = MaterialTheme.colorScheme.contentPrimary(false),
        onSurface = MaterialTheme.colorScheme.contentPrimary(false),
        error = MaterialTheme.colorScheme.notificationError(false),
    )
