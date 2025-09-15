package nl.rijksoverheid.mgo.feature.settings.home

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.mgo.MgoAlertDialog
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.mgo.getMgoAppBarScrollBehaviour
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.component.theme.symbolsPrimary
import nl.rijksoverheid.mgo.component.theme.theme.AppTheme
import nl.rijksoverheid.mgo.feature.settings.home.SettingsHomeScreenTestTag.RESET_APP_BUTTON
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

object SettingsHomeScreenTestTag {
  const val LIST = "SettingsHomeScreenList"
  const val RESET_APP_BUTTON = "SettingsHomeScreenResetAppButton"
}

/**
 * Composable that shows a screen where you can change different settings of the app.
 *
 * @param onNavigateToDisplaySettings Called when requested to navigate to the screen that shows display settings.
 * @param onNavigateToSecuritySettings Called when requested to navigate to the screen that shows security settings.
 * @param onNavigateToAdvancedSettings Called when requested to navigate to the screen that shows advanced settings.
 * @param onNavigateToAboutThisAppSettings Called when requested to navigate to the screen that shows about this app settings.
 * @param onNavigateToOnboarding Called when requested to navigate to the onboarding.
 */
@Composable
fun SettingsHomeScreen(
  onNavigateToDisplaySettings: () -> Unit,
  onNavigateToSecuritySettings: () -> Unit,
  onNavigateToAdvancedSettings: () -> Unit,
  onNavigateToAboutThisAppSettings: () -> Unit,
  onNavigateToOnboarding: () -> Unit,
) {
  val viewModel = hiltViewModel<SettingsHomeScreenViewModel>()
  LaunchedEffect(Unit) {
    viewModel.navigateToOnboarding.collectLatest {
      onNavigateToOnboarding()
    }
  }

  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  SettingsScreenContent(
    viewState = viewState,
    onClickDisplaySettings = onNavigateToDisplaySettings,
    onClickSecuritySettings = onNavigateToSecuritySettings,
    onClickAdvancedSettings = onNavigateToAdvancedSettings,
    onClickAboutThisAppSettings = onNavigateToAboutThisAppSettings,
    onClickResetApp = { viewModel.resetApp() },
  )
}

@Composable
private fun SettingsScreenContent(
  viewState: SettingsHomeScreenViewState,
  onClickDisplaySettings: () -> Unit,
  onClickSecuritySettings: () -> Unit,
  onClickAdvancedSettings: () -> Unit,
  onClickAboutThisAppSettings: () -> Unit,
  onClickResetApp: () -> Unit,
) {
  var showResetAppDialog by remember { mutableStateOf(false) }
  if (showResetAppDialog) {
    MgoAlertDialog(
      onDismissRequest = { showResetAppDialog = false },
      positiveButtonText = stringResource(CopyR.string.common_yes),
      negativeButtonText = stringResource(CopyR.string.common_no),
      onClickPositiveButton = {
        onClickResetApp()
        showResetAppDialog = false
      },
      onClickNegativeButton = { showResetAppDialog = false },
      heading = stringResource(CopyR.string.settings_reset_app_dialog_heading),
      subHeading = stringResource(CopyR.string.settings_reset_app_dialog_subheading),
    )
  }

  val scrollState = rememberScrollState()
  val scrollBehavior = getMgoAppBarScrollBehaviour(scrollState.canScrollForward, scrollState.canScrollBackward)

  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      MgoLargeTopAppBar(
        title = stringResource(CopyR.string.settings_heading),
        scrollBehavior = scrollBehavior,
      )
    },
    content = { contentPadding ->
      Column(
        modifier =
          Modifier
            .verticalScroll(scrollState)
            .padding(contentPadding)
            .padding(16.dp)
            .testTag(SettingsHomeScreenTestTag.LIST),
      ) {
        Text(
          modifier = Modifier.padding(top = 8.dp),
          text = stringResource(CopyR.string.settings_preferences_heading),
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.contentSecondary(),
        )

        MgoCard(
          modifier =
            Modifier
              .padding(top = 12.dp),
        ) {
          SettingsListItem(
            modifier =
              Modifier
                .fillMaxWidth()
                .clickable { onClickDisplaySettings() },
            icon = Icons.Outlined.LightMode,
            heading = CopyR.string.settings_display_heading,
            subHeading =
              when (viewState.appTheme) {
                AppTheme.SYSTEM -> CopyR.string.settings_display_system_heading
                AppTheme.LIGHT -> CopyR.string.settings_display_light
                AppTheme.DARK -> CopyR.string.settings_display_dark
              },
          )
          if (viewState.deviceHasBiometric) {
            SettingsListItem(
              modifier =
                Modifier
                  .fillMaxWidth()
                  .clickable { onClickSecuritySettings() },
              icon = Icons.Outlined.Lock,
              heading = CopyR.string.settings_security_heading,
              hasDivider = viewState.isDebug,
            )
          }
          if (viewState.isDebug) {
            SettingsListItem(
              modifier =
                Modifier
                  .fillMaxWidth()
                  .clickable { onClickAdvancedSettings() },
              icon = Icons.Outlined.Code,
              heading = CopyR.string.settings_advanced_heading,
              subHeading = CopyR.string.settings_advanced_subheading,
              hasDivider = false,
            )
          }
        }

        Text(
          modifier = Modifier.padding(top = 32.dp),
          text = stringResource(CopyR.string.settings_information_heading),
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.contentSecondary(),
        )

        MgoCard(
          modifier =
            Modifier
              .padding(top = 12.dp),
        ) {
          SettingsListItem(
            modifier =
              Modifier
                .fillMaxWidth()
                .clickable { onClickAboutThisAppSettings() },
            icon = Icons.Outlined.Smartphone,
            heading = CopyR.string.settings_about_this_app_heading,
            hasDivider = false,
          )
        }

        Text(
          modifier = Modifier.padding(top = 32.dp),
          text = stringResource(CopyR.string.settings_other_heading),
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.contentSecondary(),
        )

        MgoCard(
          modifier =
            Modifier
              .padding(top = 12.dp)
              .testTag(RESET_APP_BUTTON),
        ) {
          SettingsListItem(
            modifier =
              Modifier
                .fillMaxWidth()
                .clickable { showResetAppDialog = true },
            icon = Icons.Outlined.RestartAlt,
            heading = CopyR.string.settings_reset_app_heading,
            subHeading = CopyR.string.settings_reset_app_subheading,
            hasDivider = false,
          )
        }
      }
    },
  )
}

@Composable
private fun SettingsListItem(
  icon: ImageVector,
  @StringRes heading: Int,
  @StringRes subHeading: Int? = null,
  hasDivider: Boolean = true,
  modifier: Modifier = Modifier,
) {
  Column {
    Row(modifier = modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = MaterialTheme.colorScheme.symbolsPrimary(),
      )

      Column(modifier = Modifier.padding(start = 16.dp)) {
        Text(
          text = stringResource(heading),
          style = MaterialTheme.typography.bodyMedium,
        )
        if (subHeading != null) {
          Text(
            text = stringResource(subHeading),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.contentSecondary(),
          )
        }
      }
    }
    if (hasDivider) {
      HorizontalDivider(
        modifier = Modifier.padding(start = 56.dp),
      )
    }
  }
}

@DefaultPreviews
@Composable
internal fun SettingsHomeScreenPreview() {
  MgoTheme {
    SettingsScreenContent(
      viewState = SettingsHomeScreenViewState(appTheme = AppTheme.SYSTEM, isDebug = true, deviceHasBiometric = true),
      onClickDisplaySettings = {},
      onClickSecuritySettings = {},
      onClickAdvancedSettings = {},
      onClickAboutThisAppSettings = {},
      onClickResetApp = {},
    )
  }
}

@PreviewLightDark
@Composable
internal fun SettingsHomeScreenWithoutBiometricPreview() {
  MgoTheme {
    SettingsScreenContent(
      viewState = SettingsHomeScreenViewState(appTheme = AppTheme.SYSTEM, isDebug = true, deviceHasBiometric = false),
      onClickDisplaySettings = {},
      onClickSecuritySettings = {},
      onClickAdvancedSettings = {},
      onClickAboutThisAppSettings = {},
      onClickResetApp = {},
    )
  }
}

@PreviewLightDark
@Composable
internal fun SettingsHomeScreenWithoutDebugPreview() {
  MgoTheme {
    SettingsScreenContent(
      viewState = SettingsHomeScreenViewState(appTheme = AppTheme.SYSTEM, isDebug = false, deviceHasBiometric = true),
      onClickDisplaySettings = {},
      onClickSecuritySettings = {},
      onClickAdvancedSettings = {},
      onClickAboutThisAppSettings = {},
      onClickResetApp = {},
    )
  }
}
