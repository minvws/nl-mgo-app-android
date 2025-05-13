package nl.rijksoverheid.mgo.component.mgo

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.MgoTypography
import nl.rijksoverheid.mgo.component.theme.contentPrimary
import nl.rijksoverheid.mgo.component.theme.theme.LocalAppThemeProvider
import nl.rijksoverheid.mgo.component.theme.theme.isDarkTheme
import nl.rijksoverheid.mgo.framework.copy.R

@Composable
fun MgoMediumTopAppBar(
  title: String,
  scrollBehavior: TopAppBarScrollBehavior,
  onNavigateBack: (() -> Unit)? = null,
) {
  val adjustedTypography =
    MgoTypography.copy(
      titleLarge =
        MaterialTheme.typography.bodyLarge.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 22.sp,
        ),
      headlineSmall = MaterialTheme.typography.headlineLarge.copy(fontSize = 34.sp),
    )
  MgoTheme(
    typography = adjustedTypography,
    isDarkTheme = LocalAppThemeProvider.current.appTheme.isDarkTheme(),
  ) {
    LargeTopAppBar(
      title = {
        Text(
          modifier =
            Modifier
              .fillMaxWidth(),
          text = title,
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
              tint = MaterialTheme.colorScheme.contentPrimary(),
            )
          }
        }
      },
      colors =
        TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.background,
          scrolledContainerColor = MaterialTheme.colorScheme.background,
        ),
      scrollBehavior = scrollBehavior,
    )
  }
}
