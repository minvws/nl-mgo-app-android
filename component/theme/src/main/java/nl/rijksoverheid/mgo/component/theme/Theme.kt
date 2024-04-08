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

@Composable
fun MgoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = if (darkTheme) getDarkColorScheme() else getLightColorScheme(),
        typography = Typography(defaultFontFamily = fonts),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
            content = content,
        )
    }
}

@Composable
private fun getDarkColorScheme() =
    darkColors(
        primary = MaterialTheme.colors.actionPrimary(true),
        secondary = MaterialTheme.colors.contentSecondary(true),
        background = MaterialTheme.colors.backgroundPrimary(true),
        surface = MaterialTheme.colors.backgroundSecondary(true),
        onPrimary = MaterialTheme.colors.backgroundSecondary(true),
        onBackground = MaterialTheme.colors.contentPrimary(true),
        onSurface = MaterialTheme.colors.contentPrimary(true),
    )

@Composable
private fun getLightColorScheme() =
    lightColors(
        primary = MaterialTheme.colors.actionPrimary(false),
        secondary = MaterialTheme.colors.contentSecondary(false),
        background = MaterialTheme.colors.backgroundPrimary(false),
        surface = MaterialTheme.colors.backgroundSecondary(false),
        onPrimary = MaterialTheme.colors.backgroundSecondary(false),
        onBackground = MaterialTheme.colors.contentPrimary(false),
        onSurface = MaterialTheme.colors.contentPrimary(false),
    )
