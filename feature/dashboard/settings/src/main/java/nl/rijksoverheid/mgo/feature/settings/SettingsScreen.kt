package nl.rijksoverheid.mgo.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.composable.MgoButton
import nl.rijksoverheid.mgo.component.theme.composable.MgoButtonTheme
import nl.rijksoverheid.mgo.component.theme.composable.MgoCard
import nl.rijksoverheid.mgo.component.theme.composable.MgoScaffold
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * This screen currently only exists for debugging purposes. So no testing or snapshots need to be done for now.
 */
@Composable
fun SettingsScreen() {
    val viewModel: SettingsScreenViewModel = hiltViewModel()
    val viewState: SettingsScreenViewState by viewModel.viewState.collectAsStateWithLifecycle()
    SettingsScreenContent(
        viewState = viewState,
        onFeatureToggleChanged = { id, enabled ->
            viewModel.onFeatureToggleChanged(id, enabled)
        },
        onResetAppButtonClicked = {
        },
    )
}

@Composable
private fun SettingsScreenContent(
    viewState: SettingsScreenViewState,
    onFeatureToggleChanged: (FeatureToggleId, Boolean) -> Unit,
    onResetAppButtonClicked: () -> Unit,
) {
    MgoScaffold(
        appBarTitle = stringResource(CopyR.string.settings_heading),
        scrollable = false,
        contentPadding = PaddingValues(16.dp),
        content = {
            Column {
                FeatureToggleListItem(
                    featureToggle = viewState.featureToggleFlagSecure,
                    onCheckedChange = { checked -> onFeatureToggleChanged(viewState.featureToggleFlagSecure.id, checked) },
                )
                Spacer(modifier = Modifier.weight(1f))
                MgoButton(
                    modifier = Modifier.fillMaxWidth(),
                    buttonText = "Reset de app",
                    buttonTheme = MgoButtonTheme.PRIMARY_NEGATIVE,
                    onClick = onResetAppButtonClicked,
                )
            }
        },
    )
}

@Composable
private fun FeatureToggleListItem(
    featureToggle: FeatureToggle,
    onCheckedChange: (checked: Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    MgoCard(modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = featureToggle.getHeading(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
            )
            Switch(checked = featureToggle.enabled, onCheckedChange = onCheckedChange)
        }
    }
}

@Composable
private fun FeatureToggle.getHeading(): String {
    return when (this.id) {
        FeatureToggleId.FlagSecureEnabled -> stringResource(CopyR.string.settings_featureflag_localization)
    }
}

@PreviewLightDark
@Composable
private fun SettingsScreenPreview() {
    MgoTheme {
        SettingsScreenContent(
            viewState =
                SettingsScreenViewState.initialState(
                    featureToggleFlagSecure =
                        FeatureToggle(
                            FeatureToggleId.FlagSecureEnabled,
                            false,
                        ),
                ),
            onFeatureToggleChanged = { _, _ -> },
            onResetAppButtonClicked = {},
        )
    }
}
