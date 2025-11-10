package nl.rijksoverheid.mgo.component.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.SettingsApplications
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import nl.rijksoverheid.mgo.component.theme.theme.AppTheme
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
    primary = MaterialTheme.colorScheme.ActionsSolidBackground(true),
    secondary = MaterialTheme.colorScheme.ActionsTonalBackground(true),
    background = MaterialTheme.colorScheme.BackgroundsPrimary(true),
    surface = MaterialTheme.colorScheme.BackgroundsSecondary(true),
    surfaceVariant = MaterialTheme.colorScheme.BackgroundsSecondary(true),
    onPrimary = MaterialTheme.colorScheme.LabelsPrimary(true),
    onSecondary = MaterialTheme.colorScheme.ActionsTonalText(true),
    onBackground = MaterialTheme.colorScheme.LabelsPrimary(true),
    onSurface = MaterialTheme.colorScheme.LabelsPrimary(true),
    error = MaterialTheme.colorScheme.StatesCritical(true),
    surfaceContainerHigh = MaterialTheme.colorScheme.BackgroundsTertiary(true),
    surfaceContainerLow = MaterialTheme.colorScheme.BackgroundsPrimary(true), // Container color of bottom sheet,
    outlineVariant = MaterialTheme.colorScheme.SeparatorsSecondary(true), // Divider color
  )

@Composable
private fun getLightColorScheme() =
  lightColorScheme(
    primary = MaterialTheme.colorScheme.ActionsSolidBackground(false),
    secondary = MaterialTheme.colorScheme.ActionsTonalBackground(false),
    background = MaterialTheme.colorScheme.BackgroundsPrimary(false),
    surface = MaterialTheme.colorScheme.BackgroundsSecondary(false),
    surfaceVariant = MaterialTheme.colorScheme.BackgroundsSecondary(false),
    onPrimary = MaterialTheme.colorScheme.BackgroundsSecondary(false),
    onSecondary = MaterialTheme.colorScheme.ActionsTonalText(false),
    onBackground = MaterialTheme.colorScheme.LabelsPrimary(false),
    onSurface = MaterialTheme.colorScheme.LabelsPrimary(false),
    error = MaterialTheme.colorScheme.StatesCritical(false),
    surfaceContainerHigh = MaterialTheme.colorScheme.BackgroundsTertiary(false),
    surfaceContainerLow = MaterialTheme.colorScheme.BackgroundsPrimary(false), // Container color of bottom sheet
    outlineVariant = MaterialTheme.colorScheme.SeparatorsSecondary(false), // Divider color
  )

fun AppTheme.getIcon(): ImageVector =
  when (this) {
    AppTheme.SYSTEM -> Icons.Outlined.SettingsApplications
    AppTheme.LIGHT -> Icons.Outlined.LightMode
    AppTheme.DARK -> Icons.Outlined.DarkMode
  }

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
fun ColorScheme.CategoriesRijkslint(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) LogoBlue300 else LogoBlue500

@Composable
fun ColorScheme.CategoriesMedication(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) DarkGreen300 else DarkGreen500

@Composable
fun ColorScheme.CategoriesContacts(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) DarkBlue300 else DarkBlue500

@Composable
fun ColorScheme.CategoriesLaboratory(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) SkyBlue300 else SkyBlue500

@Composable
fun ColorScheme.CategoriesMental(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Purple300 else Purple500

@Composable
fun ColorScheme.CategoriesDevice(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Moss300 else Moss500

@Composable
fun ColorScheme.CategoriesVitals(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Ruby300 else Ruby500

@Composable
fun ColorScheme.CategoriesDocuments(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) DarkBrown300 else DarkBrown500

@Composable
fun ColorScheme.CategoriesVaccinations(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Mint300 else Mint800

@Composable
fun ColorScheme.CategoriesAllergies(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Orange300 else Orange500

@Composable
fun ColorScheme.CategoriesProblems(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Red300 else Red500

@Composable
fun ColorScheme.CategoriesAdministration(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Pink300 else Pink700

@Composable
fun ColorScheme.CategoriesWarning(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) DarkYellow300 else DarkYellow800

@Composable
fun ColorScheme.CategoriesProviders(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) LightBlue300 else LightBlue800

@Composable
fun ColorScheme.CategoriesProcedures(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Violet300 else Violet500

@Composable
fun ColorScheme.CategoriesLifestyle(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Green300 else Green500

@Composable
fun ColorScheme.CategoriesPlan(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Brown300 else Brown500

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

// States

@Composable
fun ColorScheme.StatesInformative(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) DarkBlue300 else DarkBlue500

@Composable
fun ColorScheme.StatesPositive(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) DarkGreen300 else DarkGreen500

@Composable
fun ColorScheme.StatesWarning(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Yellow300 else Yellow500

@Composable
fun ColorScheme.StatesCritical(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) = if (isSystemDarkTheme) Red300 else Red500
