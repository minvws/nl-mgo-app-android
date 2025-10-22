package nl.rijksoverheid.mgo.component.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import nl.rijksoverheid.mgo.component.theme.theme.LocalAppThemeProvider
import nl.rijksoverheid.mgo.component.theme.theme.isDarkTheme

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
    surfaceContainerLow = MaterialTheme.colorScheme.backgroundPrimary(true), // Container color of bottom sheet,
    outlineVariant = MaterialTheme.colorScheme.borderSecondary(true), // Divider color
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
    surfaceContainerLow = MaterialTheme.colorScheme.backgroundPrimary(false), // Container color of bottom sheet
    outlineVariant = MaterialTheme.colorScheme.borderSecondary(false), // Divider color
  )

// Backgrounds

@Composable
fun ColorScheme.BackgroundsPrimary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Gray950 else Gray50

@Composable
fun ColorScheme.BackgroundsSecondary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Gray900 else White

@Composable
fun ColorScheme.BackgroundsTertiary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Gray800 else Gray100

// Labels
@Composable
fun ColorScheme.LabelsPrimary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) White else Black

@Composable
fun ColorScheme.LabelsSecondary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) Gray300 else Gray500

@Composable
fun ColorScheme.LabelsInvert(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) Gray900 else White

@Composable
fun ColorScheme.LabelsVibrant(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) Gray100 else Gray900

// Seperators

@Composable
fun ColorScheme.SeperatorsPrimary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Gray700 else Gray200

@Composable
fun ColorScheme.SeparatorsSecondary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Gray800 else Gray100

@Composable
fun ColorScheme.SeparatorsInvert(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) White else Black

// Symbols

@Composable
fun ColorScheme.SymbolsPrimary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) Gray300 else Gray500

@Composable
fun ColorScheme.SymbolsSecondary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Gray400 else Gray400

@Composable
fun ColorScheme.SymbolsTertiary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) Gray500 else Gray300

// Categories

@Composable
fun ColorScheme.Rijkslint(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) LogoBlue300 else LogoBlue500

@Composable
fun ColorScheme.Medication(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) DarkGreen300 else DarkGreen500

@Composable
fun ColorScheme.Contacts(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) DarkBlue300 else DarkBlue500

@Composable
fun ColorScheme.Laboratory(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) SkyBlue300 else SkyBlue500

@Composable
fun ColorScheme.Mental(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) Purple300 else Purple500

@Composable
fun ColorScheme.Device(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) Moss300 else Moss500

@Composable
fun ColorScheme.Vitals(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) Ruby300 else Ruby500

@Composable
fun ColorScheme.Documents(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) DarkBrown300 else DarkBrown500

@Composable
fun ColorScheme.Vaccinations(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) Mint300 else Mint800

@Composable
fun ColorScheme.Allergies(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) Orange300 else Orange500

@Composable
fun ColorScheme.Problems(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) Red300 else Red500

@Composable
fun ColorScheme.Administration(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) Pink300 else Pink700

@Composable
fun ColorScheme.Warning(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) DarkYellow300 else DarkYellow800

@Composable
fun ColorScheme.Providers(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) LightBlue300 else LightBlue800

@Composable
fun ColorScheme.Procedures(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) Violet300 else Violet500

@Composable
fun ColorScheme.Lifestyle(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) Green300 else Green500

@Composable
fun ColorScheme.Plan(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) Brown300 else Brown500

// Actions

@Composable
fun ColorScheme.ActionsSolidBackground(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) LogoBlue300 else LogoBlue500

@Composable
fun ColorScheme.ActionsSolidText(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) Gray900 else White

@Composable
fun ColorScheme.ActionsTonalBackground(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  ActionsSolidBackground(isSystemDarkTheme).copy(alpha = 0.10f)

@Composable
fun ColorScheme.ActionsTonalText(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) LogoBlue300 else LogoBlue500

@Composable
fun ColorScheme.ActionsGhostText(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) LogoBlue300 else LogoBlue500
