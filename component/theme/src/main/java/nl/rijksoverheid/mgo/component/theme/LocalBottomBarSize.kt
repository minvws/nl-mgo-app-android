package nl.rijksoverheid.mgo.component.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp

data class BottomBarSize(val size: Dp = Dp.Unspecified)

val LocalBottomBarSize = compositionLocalOf { BottomBarSize() }
