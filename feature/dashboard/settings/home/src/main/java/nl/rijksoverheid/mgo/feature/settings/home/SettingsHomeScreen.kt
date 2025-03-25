package nl.rijksoverheid.mgo.feature.settings.home

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.RestartAlt
import androidx.compose.material.icons.outlined.Smartphone
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoScaffold
import nl.rijksoverheid.mgo.component.mgo.MgoScaffoldScrollStateProvider
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.borderSecondary
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.component.theme.symbolsPrimary
import nl.rijksoverheid.mgo.component.theme.theme.AppTheme
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

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
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    SettingsScreenContent(
        viewState = viewState,
        onClickDisplaySettings = onNavigateToDisplaySettings,
        onClickSecuritySettings = onNavigateToSecuritySettings,
        onClickAdvancedSettings = onNavigateToAdvancedSettings,
        onClickAboutThisAppSettings = onNavigateToAboutThisAppSettings,
        onClickLogout = {},
        onClickResetApp = {},
    )
}

@Composable
private fun SettingsScreenContent(
    viewState: SettingsHomeScreenViewState,
    onClickDisplaySettings: () -> Unit,
    onClickSecuritySettings: () -> Unit,
    onClickAdvancedSettings: () -> Unit,
    onClickAboutThisAppSettings: () -> Unit,
    onClickLogout: () -> Unit,
    onClickResetApp: () -> Unit,
) {
    MgoScaffold(
        appBarTitle = stringResource(CopyR.string.settings_heading),
        scrollStateProvider =
            MgoScaffoldScrollStateProvider.Column(
                rememberScrollState(),
            ),
        content = {
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
                if (viewState.biometricEnabled) {
                    SettingsListItem(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .clickable { onClickSecuritySettings() },
                        icon = Icons.Outlined.Lock,
                        heading = CopyR.string.settings_security_heading,
                    )
                }
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
                        .padding(top = 12.dp),
            ) {
                SettingsListItem(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { onClickAboutThisAppSettings() },
                    icon = Icons.AutoMirrored.Outlined.Logout,
                    heading = CopyR.string.settings_log_out_heading,
                    subHeading = CopyR.string.settings_log_out_subheading,
                )

                SettingsListItem(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { onClickResetApp() },
                    icon = Icons.Outlined.RestartAlt,
                    heading = CopyR.string.settings_reset_app_heading,
                    subHeading = CopyR.string.settings_reset_app_subheading,
                    hasDivider = false,
                )
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
                color = MaterialTheme.colorScheme.borderSecondary(),
            )
        }
    }
}

@DefaultPreviews
@Composable
internal fun SettingsHomeScreenWithBiometricPreview() {
    MgoTheme {
        SettingsScreenContent(
            viewState = SettingsHomeScreenViewState(appTheme = AppTheme.SYSTEM, biometricEnabled = true),
            onClickDisplaySettings = {},
            onClickSecuritySettings = {},
            onClickAdvancedSettings = {},
            onClickAboutThisAppSettings = {},
            onClickLogout = {},
            onClickResetApp = {},
        )
    }
}

@PreviewLightDark
@Composable
internal fun SettingsHomeScreenWithoutBiometricPreview() {
    MgoTheme {
        SettingsScreenContent(
            viewState = SettingsHomeScreenViewState(appTheme = AppTheme.SYSTEM, biometricEnabled = false),
            onClickDisplaySettings = {},
            onClickSecuritySettings = {},
            onClickAdvancedSettings = {},
            onClickAboutThisAppSettings = {},
            onClickLogout = {},
            onClickResetApp = {},
        )
    }
}
