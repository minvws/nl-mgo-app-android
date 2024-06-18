package nl.rijksoverheid.mgo.component.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
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
    val backgroundColor = MaterialTheme.colors.backgroundPrimary().toArgb()
    if (!view.isInEditMode) {
        SideEffect {
            val activity = view.context as Activity
            activity.window.statusBarColor = backgroundColor
            WindowCompat.getInsetsController(activity.window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colors = if (darkTheme) getDarkColorScheme() else getLightColorScheme(),
        typography = Typography(),
    ) {
        Surface(
            modifier = modifier.safeDrawingPadding(),
            color = MaterialTheme.colors.background,
            content = content,
        )
    }
}

@Composable
private fun getDarkColorScheme() =
    darkColors(
        primary = MaterialTheme.colors.actionPrimaryBackground(true),
        secondary = MaterialTheme.colors.actionSecondaryBackground(true),
        background = MaterialTheme.colors.backgroundPrimary(true),
        surface = MaterialTheme.colors.backgroundSecondary(true),
        onPrimary = MaterialTheme.colors.backgroundSecondary(false),
        onSecondary = MaterialTheme.colors.actionSecondaryText(true),
        onBackground = MaterialTheme.colors.contentPrimary(true),
        onSurface = MaterialTheme.colors.contentPrimary(true),
        error = MaterialTheme.colors.notificationError(true),
    )

@Composable
private fun getLightColorScheme() =
    lightColors(
        primary = MaterialTheme.colors.actionPrimaryBackground(false),
        secondary = MaterialTheme.colors.actionSecondaryBackground(false),
        background = MaterialTheme.colors.backgroundPrimary(false),
        surface = MaterialTheme.colors.backgroundSecondary(false),
        onPrimary = MaterialTheme.colors.backgroundSecondary(false),
        onSecondary = MaterialTheme.colors.actionSecondaryText(false),
        onBackground = MaterialTheme.colors.contentPrimary(false),
        onSurface = MaterialTheme.colors.contentPrimary(false),
        error = MaterialTheme.colors.notificationError(false),
    )
