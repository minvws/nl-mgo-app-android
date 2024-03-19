package nl.rijksoverheid.mgo.component.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

private val DarkColorScheme =
    darkColors(
        primary = SkyBlue,
        secondary = Color.Blue,
        background = BackgroundDark,
        surface = Color.Gray,
        onBackground = White,
        onSurface = Color.White,
    )

private val LightColorScheme =
    lightColors(
        primary = SkyBlue,
        secondary = Color.Blue,
        background = BackgroundLight,
        surface = Color.Gray,
        onBackground = Black,
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
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background,
    ) {
        MaterialTheme(
            colors = if (darkTheme) DarkColorScheme else LightColorScheme,
            typography = Typography,
            content = content,
        )
    }
}
