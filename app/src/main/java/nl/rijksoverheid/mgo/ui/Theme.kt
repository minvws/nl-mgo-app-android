package nl.rijksoverheid.mgo.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.Green,
    secondary = Color.Blue,
    tertiary = Color.Red,
    background = Color.Black,
    surface = Color.Gray,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = Color.Green,
    secondary = Color.Blue,
    tertiary = Color.Red,
    background = Color.White,
    surface = Color.Gray,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun MgoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        content = content
    )
}