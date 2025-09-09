package nl.rijksoverheid.mgo.component.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import nl.rijksoverheid.mgo.component.theme.theme.LocalAppThemeProvider
import nl.rijksoverheid.mgo.component.theme.theme.isDarkTheme

// Base
@Composable
fun ColorScheme.backgroundPrimary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF121212) else Color(0xFFF5F5F5)

@Composable
fun ColorScheme.backgroundSecondary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF242424) else Color(0xFFFFFFFF)

@Composable
fun ColorScheme.backgroundTertiary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF444444) else Color(0xFFE7E7E7)

// Content
@Composable
fun ColorScheme.contentPrimary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFFFFFFF) else Color(0xFF000000)

@Composable
fun ColorScheme.contentSecondary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFB0B0B0) else Color(0xFF6D6D6D)

@Composable
fun ColorScheme.contentInvert(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF242424) else Color(0xFFFFFFFF)

// Symbols

@Composable
fun ColorScheme.symbolsPrimary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFB0B0B0) else Color(0xFF6D6D6D)

@Composable
fun ColorScheme.symbolsSecondary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF888888) else Color(0xFF888888)

@Composable
fun ColorScheme.symbolsTertiary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF6D6D6D) else Color(0xFFB0B0B0)

// Borders

@Composable
fun ColorScheme.borderPrimary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF4F4F4F) else Color(0xFFD1D1D1)

@Composable
fun ColorScheme.borderSecondary(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF4D4D4D) else Color(0xFFE7E7E7)

// Interactive

@Composable
fun ColorScheme.interactivePrimaryDefaultBackground(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF007BC7) else Color(0xFF007BC7)

@Composable
fun ColorScheme.interactivePrimaryDefaultText(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFFFFFFF) else Color(0xFFFFFFFF)

@Composable
fun ColorScheme.interactivePrimaryCriticalBackground(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFD52B1E) else Color(0xFFD52B1E)

@Composable
fun ColorScheme.interactivePrimaryCriticalText(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFFFFFFF) else Color(0xFFFFFFFF)

@Composable
fun ColorScheme.interactiveSecondaryDefaultBackground(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) {
    Color(0xFF7CCEFD).copy(alpha = 0.10f).compositeOver(Color(0xFF000000))
  } else {
    Color(0xFF0162A3).copy(alpha = 0.10f).compositeOver(Color(0xFFFFFFFF))
  }

@Composable
fun ColorScheme.interactiveSecondaryDefaultText(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF36b6fa) else Color(0xFF0162A3)

@Composable
fun ColorScheme.interactiveSecondaryCriticalBackground(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) {
    Color(0xFFFB786E).copy(alpha = 0.10f).compositeOver(Color.Black)
  } else {
    Color(0xFFBC2519)
      .copy(alpha = 0.10f)
      .compositeOver(Color.White)
  }

@Composable
fun ColorScheme.interactiveSecondaryCriticalText(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFFB786E) else Color(0xFFBC2519)

@Composable
fun ColorScheme.interactiveTertiaryDefaultText(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF36b6fa) else Color(0xFF0162A3)

@Composable
fun ColorScheme.interactiveTertiaryCriticalText(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFFB786E) else Color(0xFFBC2519)

// Sentiment

@Composable
fun ColorScheme.sentimentInformative(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF7BD7FE) else Color(0xFF01689B)

@Composable
fun ColorScheme.sentimentPositive(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF67C183) else Color(0xFF39870C)

@Composable
fun ColorScheme.sentimentWarning(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFFFE488) else Color(0xFFE9C609)

@Composable
fun ColorScheme.sentimentCritical(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFFB786E) else Color(0xFFD52B1E)

// Support

@Composable
fun ColorScheme.supportMedication(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF9BDAAE) else Color(0xFF2A6B3E)

@Composable
fun ColorScheme.supportTreatment(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFFF9CD9) else Color(0xFFCA005D)

@Composable
fun ColorScheme.supportContacts(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF7BD7FE) else Color(0xFF01689B)

@Composable
fun ColorScheme.supportLaboratory(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF8FCAE7) else Color(0xFF8FCAE7)

@Composable
fun ColorScheme.supportFunctional(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFDBBDF5) else Color(0xFF8437B9)

@Composable
fun ColorScheme.supportDevice(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFBBC500) else Color(0xFF8C9500)

@Composable
fun ColorScheme.supportVitals(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFE351A8) else Color(0xFFF092CD)

@Composable
fun ColorScheme.supportDocuments(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFC5A509) else Color(0xFF94710A)

@Composable
fun ColorScheme.supportAllergies(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFFFCF48) else Color(0xFFE17000)

@Composable
fun ColorScheme.supportProblems(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFFF9AE6) else Color(0xFFDA007D)

@Composable
fun ColorScheme.supportPersonal(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFB0B0B0) else Color(0xFF888888)

@Composable
fun ColorScheme.supportRijkslint(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF738EAB) else Color(0xFF154273)

@Composable
fun ColorScheme.supportWarning(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFFDFA8B) else Color(0xFFF9E11E)

@Composable
fun ColorScheme.supportFysiotherapeut(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF777C00) else Color(0xFFADAF66)

@Composable
fun ColorScheme.supportPayer(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFE2B38F) else Color(0xFF673327)

@Composable
fun ColorScheme.supportVaccinations(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFA9E6D1) else Color(0xFF76D2B6)

@Composable
fun ColorScheme.supportProcedures(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFFFE488) else Color(0xFFFFB612)

@Composable
fun ColorScheme.supportLifestyle(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFA0F75F) else Color(0xFF46A808)
