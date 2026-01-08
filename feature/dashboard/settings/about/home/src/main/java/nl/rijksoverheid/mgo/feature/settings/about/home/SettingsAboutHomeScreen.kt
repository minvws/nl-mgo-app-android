package nl.rijksoverheid.mgo.feature.settings.about.home

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoTopAppBar
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.SymbolsPrimary
import nl.rijksoverheid.mgo.component.theme.theme.AppTheme
import nl.rijksoverheid.mgo.component.theme.theme.LocalAppThemeProvider
import nl.rijksoverheid.mgo.feature.settings.about.R
import nl.rijksoverheid.mgo.framework.util.launchBrowser
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun SettingsAboutHomeScreen(
  onNavigateToSecureUse: () -> Unit,
  onNavigateToOpenSource: () -> Unit,
  onNavigateToAccessibility: () -> Unit,
  onNavigateToVersions: () -> Unit,
  onNavigateBack: () -> Unit,
) {
  val viewModel = hiltViewModel<SettingsAboutHomeViewModel>()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  SettingsAboutHomeScreenContent(
    viewState = viewState,
    onClickSecureUse = onNavigateToSecureUse,
    onClickOpenSource = onNavigateToOpenSource,
    onClickAccessibility = onNavigateToAccessibility,
    onClickBack = onNavigateBack,
    onClickVersion = onNavigateToVersions,
  )
}

@Composable
private fun SettingsAboutHomeScreenContent(
  viewState: SettingsAboutHomeScreenViewState,
  onClickSecureUse: () -> Unit,
  onClickOpenSource: () -> Unit,
  onClickAccessibility: () -> Unit,
  onClickBack: () -> Unit,
  onClickVersion: () -> Unit,
) {
  val context = LocalContext.current

  Scaffold(
    topBar = {
      MgoTopAppBar(
        title = stringResource(CopyR.string.settings_about_this_app_heading),
        onNavigateBack = onClickBack,
      )
    },
    content = { contentPadding ->
      Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(contentPadding).padding(all = 16.dp)) {
        MgoCard(
          modifier =
            Modifier
              .padding(top = 8.dp),
        ) {
          val image =
            when (LocalAppThemeProvider.current.appTheme) {
              AppTheme.SYSTEM -> R.drawable.illustration_vws
              AppTheme.LIGHT -> R.drawable.illustration_vws_light
              AppTheme.DARK -> R.drawable.illustration_vws_night
            }
          Image(
            modifier = Modifier.align(Alignment.CenterHorizontally).padding(start = 100.dp),
            painter = painterResource(image),
            contentDescription = null,
          )
          SettingsAboutHomeListItem(
            modifier =
              Modifier
                .padding(top = 16.dp)
                .clickable { onClickVersion() },
            heading = CopyR.string.common_app_name,
            headingBold = true,
            subHeading =
              "${stringResource(CopyR.string.settings_about_this_app_version)} ${viewState.appVersionName} " +
                "(${viewState.appVersionCode})",
          )
          SettingsAboutHomeListItem(
            modifier = Modifier.clickable { onClickSecureUse() },
            heading = CopyR.string.settings_about_this_app_safety,
          )
          SettingsAboutHomeListItem(
            modifier = Modifier.clickable { onClickOpenSource() },
            heading = CopyR.string.settings_about_this_app_open_source,
          )
          SettingsAboutHomeListItem(
            modifier = Modifier.clickable { onClickAccessibility() },
            heading = CopyR.string.settings_about_this_app_accessibility,
            hasDivider = false,
          )
        }

        MgoCard(
          modifier =
            Modifier
              .padding(top = 32.dp, bottom = 4.dp),
        ) {
          val url = stringResource(viewState.privacyUrl)
          SettingsAboutHomeListItem(
            modifier =
              Modifier.clickable {
                context.launchBrowser(url)
              },
            heading = CopyR.string.settings_about_this_app_privacy,
            hasDivider = false,
            icon = Icons.AutoMirrored.Default.OpenInNew,
          )
        }
      }
    },
  )
}

@Composable
private fun SettingsAboutHomeListItem(
  @StringRes heading: Int,
  headingBold: Boolean = false,
  icon: ImageVector? = null,
  subHeading: String? = null,
  hasDivider: Boolean = true,
  modifier: Modifier = Modifier,
) {
  Column {
    Row(modifier = modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
      Column(
        modifier =
          Modifier
            .weight(1f),
      ) {
        Text(
          text = stringResource(heading),
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = if (headingBold) FontWeight.Bold else FontWeight.Normal,
        )
        if (subHeading != null) {
          Text(
            text = subHeading,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.LabelsSecondary(),
          )
        }
      }

      if (icon != null) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.SymbolsPrimary(),
        )
      }
    }
    if (hasDivider) {
      HorizontalDivider(
        modifier = Modifier.padding(start = 16.dp),
      )
    }
  }
}

@DefaultPreviews
@Composable
internal fun SettingsAboutHomeScreenPreview() {
  MgoTheme {
    SettingsAboutHomeScreenContent(
      viewState =
        SettingsAboutHomeScreenViewState(
          appVersionCode = 1,
          appVersionName = "1.0.0",
          privacyUrl = 0,
        ),
      onClickSecureUse = {},
      onClickOpenSource = {},
      onClickAccessibility = {},
      onClickVersion = {},
      onClickBack = {},
    )
  }
}
