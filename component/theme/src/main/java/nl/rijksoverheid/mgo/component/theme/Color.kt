package nl.rijksoverheid.mgo.component.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import nl.rijksoverheid.mgo.component.theme.theme.LocalAppThemeProvider
import nl.rijksoverheid.mgo.component.theme.theme.isDarkTheme

// Basic
val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)

// Gray
val Gray50 = Color(0xFFf5f5f5)
val Gray100 = Color(0xFFe7e7e7)
val Gray200 = Color(0xFFd1d1d1)
val Gray300 = Color(0xFFb0b0b0)
val Gray400 = Color(0xFF888888)
val Gray500 = Color(0xFF6d6d6d)
val Gray600 = Color(0xFF5d5d5d)
val Gray700 = Color(0xFF4f4f4f)
val Gray800 = Color(0xFF3d3d3d)
val Gray900 = Color(0xFF242424)
val Gray950 = Color(0xFF121212)

// Cool Gray
val CoolGray50 = Color(0xFFf8fafc)
val CoolGray100 = Color(0xFFf1f5f9)
val CoolGray200 = Color(0xFFe2e8f0)
val CoolGray300 = Color(0xFFcbd5e1)
val CoolGray400 = Color(0xFF94a3b8)
val CoolGray500 = Color(0xFF64748b)
val CoolGray600 = Color(0xFF475569)
val CoolGray700 = Color(0xFF334155)
val CoolGray800 = Color(0xFF1e293b)
val CoolGray900 = Color(0xFF0f172a)

// Logo Blue
val LogoBlue50 = Color(0xFFdce3ea)
val LogoBlue100 = Color(0xFFb8c6d5)
val LogoBlue200 = Color(0xFF95a9c0)
val LogoBlue300 = Color(0xFF738eab)
val LogoBlue400 = Color(0xFF4f7196)
val LogoBlue500 = Color(0xFF154273)
val LogoBlue600 = Color(0xFF173a63)
val LogoBlue700 = Color(0xFF102541)

// Sky Blue
val SkyBlue50 = Color(0xFFd9ebf7)
val SkyBlue100 = Color(0xFFb2d7ee)
val SkyBlue200 = Color(0xFF8cc3e6)
val SkyBlue300 = Color(0xFF66afdd)
val SkyBlue400 = Color(0xFF409cd5)
val SkyBlue500 = Color(0xFF007bc7)

// Dark Blue
val DarkBlue50 = Color(0xFFd9e8f0)
val DarkBlue100 = Color(0xFFb2d1e1)
val DarkBlue200 = Color(0xFF8cbbd2)
val DarkBlue300 = Color(0xFF66a4c3)
val DarkBlue400 = Color(0xFF408eb4)
val DarkBlue500 = Color(0xFF01689b)
val DarkBlue600 = Color(0xFF055c87)
val DarkBlue700 = Color(0xFF0b4c6f)

// Light Blue
val LightBlue50 = Color(0xFFeef7fc)
val LightBlue100 = Color(0xFFddeff8)
val LightBlue200 = Color(0xFFcce7f4)
val LightBlue300 = Color(0xFFbcdff0)
val LightBlue400 = Color(0xFFabd7ed)
val LightBlue500 = Color(0xFF8fcae7)
val LightBlue600 = Color(0xFF57b0d9)
val LightBlue700 = Color(0xFF3197c6)
val LightBlue800 = Color(0xFF2279a7)

// Green
val Green50 = Color(0xFFe1edda)
val Green100 = Color(0xFFc3dbb5)
val Green200 = Color(0xFFa5c991)
val Green300 = Color(0xFF88b76d)
val Green400 = Color(0xFF6aa549)
val Green500 = Color(0xFF39870c)

// Dark Green
val DarkGreen50 = Color(0xFFdde6e1)
val DarkGreen100 = Color(0xFFbdccc2)
val DarkGreen200 = Color(0xFF9db4a4)
val DarkGreen300 = Color(0xFF7d9b87)
val DarkGreen400 = Color(0xFF5d8269)
val DarkGreen500 = Color(0xFF275937)

// Mint
val Mint50 = Color(0xFFeaf8f4)
val Mint100 = Color(0xFFd5f1e9)
val Mint200 = Color(0xFFc1ebde)
val Mint300 = Color(0xFFade4d3)
val Mint400 = Color(0xFF98ddc8)
val Mint500 = Color(0xFF76d2b6)
val Mint600 = Color(0xFF4ab79a)
val Mint700 = Color(0xFF309c80)
val Mint800 = Color(0xFF247d68)

// Moss
val Moss50 = Color(0xFFebebd9)
val Moss100 = Color(0xFFd6d7b2)
val Moss200 = Color(0xFFc1c38c)
val Moss300 = Color(0xFFadaf66)
val Moss400 = Color(0xFF999c40)
val Moss500 = Color(0xFF777b00)

// Yellow
val Yellow50 = Color(0xFFfefbdd)
val Yellow100 = Color(0xFFfdf6bb)
val Yellow200 = Color(0xFFfcf199)
val Yellow300 = Color(0xFFfbed78)
val Yellow400 = Color(0xFFfae856)
val Yellow500 = Color(0xFFf9e11e)
val Yellow600 = Color(0xFFe9c609)
val Yellow700 = Color(0xFFc99c05)
val Yellow800 = Color(0xFFa07008)

// Dark Yellow
val DarkYellow50 = Color(0XFFfef4db)
val DarkYellow100 = Color(0xFFfee9b7)
val DarkYellow200 = Color(0xFFfdde94)
val DarkYellow300 = Color(0xFFfdd370)
val DarkYellow400 = Color(0xFFfdc84d)
val DarkYellow500 = Color(0xFFffb612)
val DarkYellow600 = Color(0xFFf99807)
val DarkYellow700 = Color(0xFFdd7102)
val DarkYellow800 = Color(0xFFb74e06)

// Orange
val Orange50 = Color(0xFFfbead9)
val Orange100 = Color(0xFFf6d4b2)
val Orange200 = Color(0xFFf1be8c)
val Orange300 = Color(0xFFeda966)
val Orange400 = Color(0xFFe89440)
val Orange500 = Color(0xFFe17000)

// Red
val Red50 = Color(0xFFf9dfdd)
val Red100 = Color(0xFFf2bfbb)
val Red200 = Color(0xFFec9f99)
val Red300 = Color(0xFFe67f78)
val Red400 = Color(0xFFdf6056)
val Red500 = Color(0xFFd52b1e)

// Ruby
val Ruby50 = Color(0xFFf7d9e7)
val Ruby100 = Color(0xFFefb3ce)
val Ruby200 = Color(0xFFe78cb6)
val Ruby300 = Color(0xFFdf669d)
val Ruby400 = Color(0xFFd74085)
val Ruby500 = Color(0xFFca005d)

// Violet
val Violet50 = Color(0xFFf2d9e7)
val Violet100 = Color(0xFFe5b2cf)
val Violet200 = Color(0xFFd88cb7)
val Violet300 = Color(0xFFcb66a0)
val Violet400 = Color(0xFFbe4088)
val Violet500 = Color(0xFFa90061)

// Pink
val Pink50 = Color(0xFFfdeff8)
val Pink100 = Color(0xFFfbdef0)
val Pink200 = Color(0xFFf8cee8)
val Pink300 = Color(0xFFf6bde1)
val Pink400 = Color(0xFFf4add9)
val Pink500 = Color(0xFFf092cd)
val Pink600 = Color(0xFFe351a8)
val Pink700 = Color(0xFFd13189)

// Purple
val Purple50 = Color(0xFFe3dce7)
val Purple100 = Color(0xFFc6b9ce)
val Purple200 = Color(0xFFa995b7)
val Purple300 = Color(0xFF8d729f)
val Purple400 = Color(0xFF714f87)
val Purple500 = Color(0xFF42145f)

// Brown
val Brown50 = Color(0xFFefeada)
val Brown100 = Color(0xFFc6b9ce)
val Brown200 = Color(0xFFa995b7)
val Brown300 = Color(0xFF8d729f)
val Brown400 = Color(0xFF714f87)
val Brown500 = Color(0xFF42145f)

// Dark Brown
val DarkBrown50 = Color(0xFFe8e0df)
val DarkBrown100 = Color(0xFFd1c1bd)
val DarkBrown200 = Color(0xFFbaa39d)
val DarkBrown300 = Color(0xFFa3847d)
val DarkBrown400 = Color(0xFF8d665d)
val DarkBrown500 = Color(0xFF673327)

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

@Composable
fun ColorScheme.categoriesProblems(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFE67F78) else Color(0xFFD52B1E)

@Composable
fun ColorScheme.categoriesAllergies(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFEDA966) else Color(0xFFE17000)

@Composable
fun ColorScheme.categoriesWarnings(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFFDD370) else Color(0xFFB74E06)

@Composable
fun ColorScheme.categoriesVaccinations(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFADE4D3) else Color(0xFF247D68)

@Composable
fun ColorScheme.categoriesLifestyle(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF88B76D) else Color(0xFF39870C)

@Composable
fun ColorScheme.categoriesMental(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF8D729F) else Color(0xFF42145F)

@Composable
fun ColorScheme.categoriesVital(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFDF669D) else Color(0xFFCA005D)

@Composable
fun ColorScheme.categoriesLaboratory(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF66AFDD) else Color(0xFF007BC7)

@Composable
fun ColorScheme.categoriesProcedures(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFCB66A0) else Color(0xFFA90061)

@Composable
fun ColorScheme.categoriesMedication(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF7D9B87) else Color(0xFF275937)

@Composable
fun ColorScheme.categoriesContacts(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFF66A4C3) else Color(0xFF01689B)

@Composable
fun ColorScheme.categoriesDocuments(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFA3847D) else Color(0xFF673327)

@Composable
fun ColorScheme.categoriesPlan(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFBFA96C) else Color(0xFF94710A)

@Composable
fun ColorScheme.categoriesDevice(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFADAF66) else Color(0xFF777B00)

@Composable
fun ColorScheme.categoriesPersonal(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFF6BDE1) else Color(0xFFD13189)

@Composable
fun ColorScheme.categoriesPayer(isSystemDarkTheme: Boolean = LocalAppThemeProvider.current.appTheme.isDarkTheme()) =
  if (isSystemDarkTheme) Color(0xFFBCDFF0) else Color(0xFF2279A7)
