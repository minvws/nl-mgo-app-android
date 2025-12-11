package nl.rijksoverheid.mgo.component.mgo

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.rijksoverheid.mgo.component.theme.LabelsPrimary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.MgoTypography
import nl.rijksoverheid.mgo.component.theme.headlineExtraLarge
import nl.rijksoverheid.mgo.component.theme.theme.LocalAppThemeProvider
import nl.rijksoverheid.mgo.component.theme.theme.isDarkTheme
import nl.rijksoverheid.mgo.framework.copy.R

@Composable
fun MgoLargeTopAppBar(
  title: String,
  textAlign: TextAlign = TextAlign.Start,
  windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
  containerColor: Color = MaterialTheme.colorScheme.background,
  scrollBehavior: TopAppBarScrollBehavior,
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
      headlineMedium = MaterialTheme.typography.headlineExtraLarge.copy(fontSize = 34.sp),
    )
  MgoTheme(
    typography = adjustedTypography,
    isDarkTheme = LocalAppThemeProvider.current.appTheme.isDarkTheme(),
  ) {
    LargeTopAppBar(
      windowInsets = windowInsets,
      title = {
        Text(
          modifier =
            Modifier
              .fillMaxWidth()
              .padding(end = 16.dp),
          text = title,
          textAlign = textAlign,
          overflow = TextOverflow.Ellipsis,
        )
      },
      navigationIcon = {
        onNavigateBack?.let {
          IconButton(onClick = onNavigateBack) {
            Icon(
              imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
              contentDescription =
                stringResource(
                  R.string.common_previous,
                ),
              tint = MaterialTheme.colorScheme.LabelsPrimary(),
            )
          }
        }
      },
      colors =
        TopAppBarDefaults.topAppBarColors(
          containerColor = containerColor,
          scrolledContainerColor = containerColor,
        ),
      scrollBehavior = scrollBehavior,
      actions = actions,
    )
  }
}

@PreviewLightDark
@Composable
internal fun MgoLargeTopAppBarPreview() {
  MgoTheme {
    MgoLargeTopAppBar(
      title = "Large Top App Bar",
      scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
    )
  }
}

@PreviewLightDark
@Composable
internal fun MgoLargeTopAppBarWithBackButtonPreview() {
  MgoTheme {
    MgoLargeTopAppBar(
      title = "Large Top App Bar",
      onNavigateBack = {},
      scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState()),
    )
  }
}
