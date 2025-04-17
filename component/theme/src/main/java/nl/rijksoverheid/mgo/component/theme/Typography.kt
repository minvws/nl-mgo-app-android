package nl.rijksoverheid.mgo.component.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val fonts =
  FontFamily(
    Font(R.font.ro_regular, weight = FontWeight.Normal),
    Font(R.font.ro_bold, weight = FontWeight.Bold),
    Font(R.font.ro_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
  )

val MgoTypography =
  Typography(
    headlineLarge =
      TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 34.sp,
        lineHeight = 41.sp,
      ),
    headlineMedium =
      TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 34.sp,
      ),
    headlineSmall =
      TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 28.sp,
      ),
    bodyLarge =
      TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 28.sp,
      ),
    bodyMedium =
      TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 22.sp,
      ),
    bodySmall =
      TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 20.sp,
      ),
  )

val Typography.headlineExtraSmall: TextStyle
  get() =
    TextStyle(
      fontFamily = fonts,
      fontWeight = FontWeight.Bold,
      fontSize = 20.sp,
      lineHeight = 25.sp,
    )
