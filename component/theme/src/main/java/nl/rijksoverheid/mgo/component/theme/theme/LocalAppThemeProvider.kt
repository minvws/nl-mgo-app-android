package nl.rijksoverheid.mgo.component.theme.theme

import androidx.compose.runtime.compositionLocalOf

/**
 * Implementation of [LocalAppThemeProvider].
 */
class DefaultLocalAppThemeProvider(val appTheme: AppTheme)

/**
 * Use to provide let the entire app access the currently selected [AppTheme].
 */
val LocalAppThemeProvider = compositionLocalOf { DefaultLocalAppThemeProvider(AppTheme.SYSTEM) }
