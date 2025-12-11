package nl.rijksoverheid.mgo.component.mgo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.sp
import nl.rijksoverheid.mgo.component.theme.LabelsPrimary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.MgoTypography
import nl.rijksoverheid.mgo.component.theme.theme.LocalAppThemeProvider
import nl.rijksoverheid.mgo.component.theme.theme.isDarkTheme
import nl.rijksoverheid.mgo.framework.copy.R

@Composable
fun MgoTopAppBar(
  title: String,
  textAlign: TextAlign = TextAlign.Start,
  windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
  navigationIcon: ImageVector = Icons.AutoMirrored.Rounded.ArrowBack,
  containerColor: Color = MaterialTheme.colorScheme.background,
  actions: @Composable RowScope.() -> Unit = {},
  onNavigateBack: (() -> Unit)? = null,
) {
  val adjustedTypography =
    MgoTypography.copy(
      titleLarge =
        MaterialTheme.typography.bodyLarge.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 22.sp,
        ),
    )
  MgoTheme(
    typography = adjustedTypography,
    isDarkTheme = LocalAppThemeProvider.current.appTheme.isDarkTheme(),
  ) {
    TopAppBar(
      windowInsets = windowInsets,
      title = {
        Text(
          modifier =
            Modifier
              .fillMaxWidth(),
          text = title,
          textAlign = textAlign,
        )
      },
      navigationIcon = {
        onNavigateBack?.let {
          IconButton(onClick = onNavigateBack) {
            Icon(
              imageVector = navigationIcon,
              contentDescription =
                stringResource(
                  R.string.common_previous,
                ),
              tint = MaterialTheme.colorScheme.LabelsPrimary(),
            )
          }
        }
      },
      actions = actions,
      colors =
        TopAppBarDefaults.topAppBarColors(
          containerColor = containerColor,
          scrolledContainerColor = MaterialTheme.colorScheme.background,
        ),
    )
  }
}

@PreviewLightDark
@Composable
internal fun MgoTopAppBarPreview() {
  MgoTheme {
    MgoTopAppBar(
      title = "Top App Bar",
    )
  }
}

@PreviewLightDark
@Composable
internal fun MgoTopAppBarWithBackButtonPreview() {
  MgoTheme {
    MgoTopAppBar(
      title = "Top App Bar",
      onNavigateBack = {},
    )
  }
}
