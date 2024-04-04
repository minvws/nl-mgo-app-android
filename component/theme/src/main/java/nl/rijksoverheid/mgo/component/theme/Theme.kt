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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val DarkColorScheme =
    darkColors(
        primary = SkyBlue,
        secondary = Color.Blue,
        background = BackgroundDark,
        surface = Color.Black,
        onPrimary = Color.White,
        onBackground = White,
        onSurface = Color.White,
    )

private val LightColorScheme =
    lightColors(
        primary = SkyBlue,
        secondary = Color.Blue,
        background = BackgroundLight,
        onPrimary = Color.White,
        surface = Color.White,
        onBackground = Black,
        onSurface = Color.White,
    )

@Composable
fun styleLink() = if (isSystemInDarkTheme()) SkyBlueTint1 else LinkDefault

private val fonts =
    FontFamily(
        Font(R.font.ro_regular, weight = FontWeight.Normal),
        Font(R.font.ro_bold, weight = FontWeight.Bold),
        Font(R.font.ro_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    )

private val Typography = Typography(defaultFontFamily = fonts)

val Typography.headingExtraLarge: TextStyle
    get() =
        TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Bold,
            fontSize = 34.sp,
            lineHeight = 41.sp,
        )

val Typography.headingLarge: TextStyle
    get() =
        TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 34.sp,
        )

val Typography.headingRegular: TextStyle
    get() =
        TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 28.sp,
        )

val Typography.headingSmall: TextStyle
    get() =
        TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            lineHeight = 28.sp,
        )

val Typography.headingExtraSmall: TextStyle
    get() =
        TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 25.sp,
        )

val Typography.bodyText: TextStyle
    get() =
        TextStyle(
            fontFamily = fonts,
            fontWeight = FontWeight.Normal,
            fontSize = 17.sp,
            lineHeight = 22.sp,
        )

@Composable
fun MgoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
            content = content,
        )
    }
}
