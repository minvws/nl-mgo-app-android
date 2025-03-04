package nl.rijksoverheid.mgo.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.mgo.MgoAlertDialog
import nl.rijksoverheid.mgo.component.mgo.MgoButton
import nl.rijksoverheid.mgo.component.mgo.MgoButtonTheme
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoScaffold
import nl.rijksoverheid.mgo.component.mgo.MgoScaffoldScrollStateProvider
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId
import nl.rijksoverheid.mgo.framework.featuretoggle.flagSecureFeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.flagSkipPinFeatureToggle
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a screen that contains various settings you can alter for the app.
 * Note: This screen is currently build for debugging purposes, and does not implement a certain design. This will be done later.
 *
 * @param onNavigateToOnboarding Called when requested to navigate back to the onboarding.
 * @param onRestartApp Called when requested to restart the app. When the `clearData` parameter is true, it should also clear all locally
 * stored data so that the app in a state as it was just installed.
 */
@Composable
fun SettingsScreen(
    onNavigateToOnboarding: () -> Unit,
    onRestartApp: (clearData: Boolean) -> Unit,
) {
    val viewModel: SettingsScreenViewModel = hiltViewModel()
    val togglesWithState: List<FeatureToggleWithState> by viewModel.featureToggleStates.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigateToOnboarding.collectLatest {
            onNavigateToOnboarding()
        }
    }

    SettingsScreenContent(
        togglesWithState = togglesWithState,
        showToggles = viewModel.getShowToggles(),
        onFeatureToggleChanged = { id, enabled -> viewModel.onFeatureToggleChanged(id, enabled) },
        onResetAppButtonClicked = { onRestartApp(true) },
        onRestartApp = { onRestartApp(false) },
    )
}

@Composable
private fun SettingsScreenContent(
    togglesWithState: List<FeatureToggleWithState>,
    showToggles: Boolean,
    onFeatureToggleChanged: (FeatureToggle, Boolean) -> Unit,
    onResetAppButtonClicked: () -> Unit,
    onRestartApp: () -> Unit,
) {
    var showResetAppDialog by remember { mutableStateOf(false) }
    if (showResetAppDialog) {
        MgoAlertDialog(
            heading = stringResource(CopyR.string.settings_reset_app_dialog_heading),
            subHeading = stringResource(CopyR.string.settings_reset_app_dialog_subheading),
            onDismissRequest = { showResetAppDialog = false },
            positiveButtonText = stringResource(CopyR.string.common_yes),
            onClickPositiveButton = {
                showResetAppDialog = false
                onResetAppButtonClicked()
            },
            negativeButtonText = stringResource(CopyR.string.common_no),
            onClickNegativeButton = {
                showResetAppDialog = false
            },
        )
    }

    var showRestartAppDialog by remember { mutableStateOf(false) }
    if (showRestartAppDialog) {
        AlertDialog(
            title = { Text(text = "App opnieuw opstarten") },
            text = { Text(text = "Voor deze wijziging is het mogelijk nodig om de app opnieuw op te starten.") },
            onDismissRequest = { showRestartAppDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showRestartAppDialog = false
                        onRestartApp()
                    },
                ) {
                    Text("Opnieuw opstarten")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showRestartAppDialog = false
                    },
                ) {
                    Text("Later")
                }
            },
        )
    }

    MgoScaffold(
        appBarTitle = stringResource(CopyR.string.settings_heading),
        scrollStateProvider = MgoScaffoldScrollStateProvider.Column(rememberScrollState()),
        content = {
            Column(modifier = Modifier.padding(bottom = 16.dp)) {
                if (showToggles) {
                    // Do not show the toggles for the demo flavor to make the app more production realistic
                    togglesWithState.forEachIndexed { _, toggleWithState ->
                        FeatureToggleListItem(
                            modifier = Modifier.padding(bottom = 16.dp),
                            featureToggleWithState = toggleWithState,
                            onCheckedChange = { checked ->
                                onFeatureToggleChanged(toggleWithState.featureToggle, checked)
                                showRestartAppDialog = true
                            },
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                MgoButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonText = stringResource(CopyR.string.settings_reset_app_button),
                    buttonTheme = MgoButtonTheme.PRIMARY_NEGATIVE,
                    onClick = { showResetAppDialog = true },
                )
            }
        },
    )
}

@Composable
private fun FeatureToggleListItem(
    featureToggleWithState: FeatureToggleWithState,
    onCheckedChange: (checked: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    MgoCard(modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = featureToggleWithState.getHeading(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
            )
            Switch(checked = featureToggleWithState.enabled, onCheckedChange = onCheckedChange)
        }
    }
}

@Composable
private fun FeatureToggleWithState.getHeading(): String {
    return when (this.featureToggle.id) {
        FeatureToggleId.SkipPin -> "Sla pin code over"
        FeatureToggleId.FlagSecure -> "Flag secure"
        FeatureToggleId.AutomaticLocalisation -> stringResource(CopyR.string.settings_featureflag_localization)
    }
}

@PreviewLightDark
@Composable
private fun SettingsScreenContentPreview() {
    MgoTheme {
        SettingsScreenContent(
            togglesWithState =
                listOf(
                    FeatureToggleWithState(featureToggle = flagSkipPinFeatureToggle, enabled = true),
                    FeatureToggleWithState(featureToggle = flagSecureFeatureToggle, enabled = false),
                ),
            showToggles = true,
            onFeatureToggleChanged = { _, _ -> },
            onResetAppButtonClicked = {},
            onRestartApp = {},
        )
    }
}
