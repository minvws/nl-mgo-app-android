package nl.rijksoverheid.mgo.component.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Base

@Composable
fun Colors.backgroundPrimary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF050505) else Color(0xFFFAFAFA)

@Composable
fun Colors.backgroundSecondary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF1D1D1D) else Color(0xFFFFFFFF)

@Composable
fun Colors.backgroundTertiary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF404040) else Color(0xFFF3F3F3)

// Content
@Composable
fun Colors.contentPrimary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFFFFFFF) else Color(0xFF000000)

@Composable
fun Colors.contentSecondary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFE6E6E6) else Color(0xFF404040)

@Composable
fun Colors.contentTertiary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFCCCCCC) else Color(0xFF353535)

// Icons

@Composable
fun Colors.iconsPrimary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF535353) else Color(0xFF696969)

@Composable
fun Colors.iconSecondary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF999999) else Color(0xFF999999)

// Lines

@Composable
fun Colors.linesPrimary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF696969) else Color(0xFFCCCCCC)

@Composable
fun Colors.linesSecondary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF535353) else Color(0xFFE6E6E6)

@Composable
fun Colors.linesInput(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) = if (isSystemDarkTheme) Color(0xFFB4B4B4) else Color(0xFF696969)

// Support
@Composable
fun Colors.actionPrimaryBackground(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF007BC7) else Color(0xFF007BC7)

@Composable
fun Colors.actionPrimaryText(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFFFFFFF) else Color(0xFFFFFFFF)

@Composable
fun Colors.actionSecondaryBackground(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFD9EBF7) else Color(0xFFD9EBF7)

@Composable
fun Colors.actionSecondaryText(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF01689B) else Color(0xFF01689B)

@Composable
fun Colors.actionTertiaryDefault(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF8FCAE7) else Color(0xFF01689B)

@Composable
fun Colors.notificationInformation(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF66AFDD) else Color(0xFF007BC7)

@Composable
fun Colors.notificationSuccess(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF88B76D) else Color(0xFF39870C)

@Composable
fun Colors.notificationWarning(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFFDD370) else Color(0xFFFFB612)

@Composable
fun Colors.notificationError(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFE67F78) else Color(0xFFD52B1E)

@Composable
fun Colors.supportApotheek(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF7D9B87) else Color(0xFF275937)

@Composable
fun Colors.supportZiekenhuis(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFCA005D) else Color(0xFFDF669D)

@Composable
fun Colors.supportHuisarts(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF01689B) else Color(0xFF66A4C3)

@Composable
fun Colors.supportTandarts(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF8FCAE7) else Color(0xFF66A4C3)

@Composable
fun Colors.supportGgz(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) = if (isSystemDarkTheme) Color(0xFF42145F) else Color(0xFF8D729F)

@Composable
fun Colors.supportFysiotherapeut(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF777C00) else Color(0xFFADAF66)

@Composable
fun Colors.supportVerpleeghuis(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFF092CD) else Color(0xFFF6BDE1)

@Composable
fun Colors.supportThuiszorg(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFF092CD) else Color(0xFFF6BDE1)

@Composable
fun Colors.supportKliniek(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFE17000) else Color(0xFFEDA966)

@Composable
fun Colors.supportOverige(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF999999) else Color(0xFF999999)

@Composable
fun Colors.supportRijkslint(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF154273) else Color(0xFF154273)
