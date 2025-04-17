package nl.rijksoverheid.mgo.component.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MgoTheme(
  modifier: Modifier = Modifier,
  typography: Typography = MgoTypography,
  isDarkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable () -> Unit,
) {
  MaterialTheme(
    colorScheme = if (isDarkTheme) getDarkColorScheme() else getLightColorScheme(),
    typography = typography,
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
    primary = MaterialTheme.colorScheme.interactivePrimaryDefaultBackground(true),
    secondary = MaterialTheme.colorScheme.interactiveSecondaryDefaultBackground(true),
    background = MaterialTheme.colorScheme.backgroundPrimary(true),
    surface = MaterialTheme.colorScheme.backgroundSecondary(true),
    surfaceVariant = MaterialTheme.colorScheme.backgroundSecondary(true),
    onPrimary = MaterialTheme.colorScheme.backgroundSecondary(false),
    onSecondary = MaterialTheme.colorScheme.interactiveSecondaryDefaultText(true),
    onBackground = MaterialTheme.colorScheme.contentPrimary(true),
    onSurface = MaterialTheme.colorScheme.contentPrimary(true),
    error = MaterialTheme.colorScheme.sentimentCritical(true),
    surfaceContainerHigh = MaterialTheme.colorScheme.backgroundTertiary(true),
  )

@Composable
private fun getLightColorScheme() =
  lightColorScheme(
    primary = MaterialTheme.colorScheme.interactivePrimaryDefaultBackground(false),
    secondary = MaterialTheme.colorScheme.interactiveSecondaryDefaultBackground(false),
    background = MaterialTheme.colorScheme.backgroundPrimary(false),
    surface = MaterialTheme.colorScheme.backgroundSecondary(false),
    surfaceVariant = MaterialTheme.colorScheme.backgroundSecondary(false),
    onPrimary = MaterialTheme.colorScheme.backgroundSecondary(false),
    onSecondary = MaterialTheme.colorScheme.interactiveSecondaryDefaultText(false),
    onBackground = MaterialTheme.colorScheme.contentPrimary(false),
    onSurface = MaterialTheme.colorScheme.contentPrimary(false),
    error = MaterialTheme.colorScheme.sentimentCritical(false),
    surfaceContainerHigh = MaterialTheme.colorScheme.backgroundTertiary(false),
  )
