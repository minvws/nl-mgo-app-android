package nl.rijksoverheid.mgo.component.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MgoTheme(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) getDarkColorScheme() else getLightColorScheme(),
        typography = MgoTypography,
    ) {
        Surface(
            modifier = modifier,
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
        surfaceVariant = MaterialTheme.colorScheme.backgroundSecondary(true),
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
        surfaceVariant = MaterialTheme.colorScheme.backgroundSecondary(false),
        onPrimary = MaterialTheme.colorScheme.backgroundSecondary(false),
        onSecondary = MaterialTheme.colorScheme.actionSecondaryDefaultText(false),
        onBackground = MaterialTheme.colorScheme.contentPrimary(false),
        onSurface = MaterialTheme.colorScheme.contentPrimary(false),
        error = MaterialTheme.colorScheme.notificationError(false),
    )
