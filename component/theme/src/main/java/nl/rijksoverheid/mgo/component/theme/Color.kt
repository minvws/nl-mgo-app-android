package nl.rijksoverheid.mgo.component.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

// Base
@Composable
fun ColorScheme.backgroundPrimary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF050505) else Color(0xFFFAFAFA)

@Composable
fun ColorScheme.backgroundSecondary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF1D1D1D) else Color(0xFFFFFFFF)

@Composable
fun ColorScheme.backgroundTertiary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF404040) else Color(0xFFF3F3F3)

// Content
@Composable
fun ColorScheme.contentPrimary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFFFFFFF) else Color(0xFF000000)

@Composable
fun ColorScheme.contentSecondary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFE6E6E6) else Color(0xFF404040)

@Composable
fun ColorScheme.contentTertiary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFCCCCCC) else Color(0xFF535353)

// Icons

@Composable
fun ColorScheme.iconsPrimary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFB4B4B4) else Color(0xFF696969)

@Composable
fun ColorScheme.iconsSecondary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF999999) else Color(0xFF999999)

// Strokes

@Composable
fun ColorScheme.strokesPrimary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF4D4D4D) else Color(0xFFCCCCCC)

@Composable
fun ColorScheme.strokesSecondary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF333333) else Color(0xFFE0E0E0)

@Composable
fun ColorScheme.strokesTertiary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF2C2C2C) else Color(0xFFF7F7F7)

// Lines

@Composable
fun ColorScheme.linesPrimary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF696969) else Color(0xFFCCCCCC)

@Composable
fun ColorScheme.linesSecondary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF535353) else Color(0xFFE6E6E6)

@Composable
fun ColorScheme.linesTertiary(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF2C2C2C) else Color(0xFFF3F3F3)

@Composable
fun ColorScheme.linesInput(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) = if (isSystemDarkTheme) Color(0xFFB4B4B4) else Color(0xFF696969)

// Support
@Composable
fun ColorScheme.actionPrimaryDefaultBackground(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF007BC7) else Color(0xFF007BC7)

@Composable
fun ColorScheme.actionPrimaryDefaultText(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFFFFFFF) else Color(0xFFFFFFFF)

@Composable
fun ColorScheme.actionPrimaryNegativeBackground(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFD52B1E) else Color(0xFFD52B1E)

@Composable
fun ColorScheme.actionPrimaryNegativeText(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color.White else Color.White

@Composable
fun ColorScheme.actionSecondaryDefaultBackground(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) {
        Color(0xFF007BC7).copy(alpha = 0.24f).compositeOver(Color.Black)
    } else {
        Color(0xFF007BC7).copy(alpha = 0.12f)
            .compositeOver(Color.White)
    }

@Composable
fun ColorScheme.actionSecondaryDefaultText(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF8FCAE7) else Color(0xFF01689B)

@Composable
fun ColorScheme.actionSecondaryNegativeBackground(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) {
        Color(0xFFFFFFFF).copy(alpha = 0.10f).compositeOver(Color.Black)
    } else {
        Color(0xFF000000).copy(alpha = 0.05f)
            .compositeOver(Color.White)
    }

@Composable
fun ColorScheme.actionSecondaryNegativeText(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFE67F78) else Color(0xFFD52B1E)

@Composable
fun ColorScheme.actionTertiaryDefaultText(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF8FCAE7) else Color(0xFF01689B)

@Composable
fun ColorScheme.actionTertiaryNegativeText(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFE67F78) else Color(0xFFD52B1E)

@Composable
fun ColorScheme.notificationInformation(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF66AFDD) else Color(0xFF007BC7)

@Composable
fun ColorScheme.notificationSuccess(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF88B76D) else Color(0xFF39870C)

@Composable
fun ColorScheme.notificationWarning(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFFDD370) else Color(0xFFFFB612)

@Composable
fun ColorScheme.notificationError(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFE67F78) else Color(0xFFD52B1E)

@Composable
fun ColorScheme.supportApotheek(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF7D9B87) else Color(0xFF275937)

@Composable
fun ColorScheme.supportZiekenhuis(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFDF669D) else Color(0xFFCA005D)

@Composable
fun ColorScheme.supportHuisarts(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF66A4C3) else Color(0xFF01689B)

@Composable
fun ColorScheme.supportTandarts(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF66A4C3) else Color(0xFF8FCAE7)

@Composable
fun ColorScheme.supportGgz(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) = if (isSystemDarkTheme) Color(0xFF8D729F) else Color(0xFF42145F)

@Composable
fun ColorScheme.supportFysiotherapeut(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF777C00) else Color(0xFFADAF66)

@Composable
fun ColorScheme.supportVerpleeghuis(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFF6BDE1) else Color(0xFFF092CD)

@Composable
fun ColorScheme.supportThuiszorg(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFBFA96C) else Color(0xFF94710A)

@Composable
fun ColorScheme.supportKliniek(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFEDA966) else Color(0xFFE17000)

@Composable
fun ColorScheme.supportOverige(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF999999) else Color(0xFF999999)

@Composable
fun ColorScheme.supportRijkslint(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF738EAB) else Color(0xFF154273)

@Composable
fun ColorScheme.supportGgd(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) = if (isSystemDarkTheme) Color(0xFFA3847D) else Color(0xFF673327)

@Composable
fun ColorScheme.supportRivm(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) = if (isSystemDarkTheme) Color(0xFFFBED78) else Color(0xFFF9E11E)

@Composable
fun ColorScheme.supportVerloskundige(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFCB66A0) else Color(0xFFA90061)

@Composable
fun ColorScheme.supportRevalidatie(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFFACE4D3) else Color(0xFF76D2B6)

@Composable
fun ColorScheme.supportGegevens(isSystemDarkTheme: Boolean = isSystemInDarkTheme()) =
    if (isSystemDarkTheme) Color(0xFF34C759) else Color(0xFF34C759)
