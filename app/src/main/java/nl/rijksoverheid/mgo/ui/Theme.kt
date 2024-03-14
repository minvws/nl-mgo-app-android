package nl.rijksoverheid.mgo.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import nl.rijksoverheid.mgo.R

private val DarkColorScheme =
    darkColors(
        primary = Color.Green,
        secondary = Color.Blue,
        background = Color.Black,
        surface = Color.Gray,
        onBackground = Color.White,
        onSurface = Color.White,
    )

private val LightColorScheme =
    lightColors(
        primary = Color.Green,
        secondary = Color.Blue,
        background = Color.White,
        surface = Color.Gray,
        onBackground = Color.Black,
        onSurface = Color.Black,
    )

private val fonts =
    FontFamily(
        Font(R.font.ro_regular, weight = FontWeight.Normal),
        Font(R.font.ro_bold, weight = FontWeight.Bold),
        Font(R.font.ro_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    )

private val Typography = Typography(defaultFontFamily = fonts)

@Composable
fun MgoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorScheme else LightColorScheme,
        content = content,
        typography = Typography,
    )
}
