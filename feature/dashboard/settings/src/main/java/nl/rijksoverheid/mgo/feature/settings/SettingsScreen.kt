package nl.rijksoverheid.mgo.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId
import nl.rijksoverheid.mgo.framework.featuretoggle.featureToggles
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * This screen currently only exists for debugging purposes. So no testing or snapshots need to be done for now.
 */
@Composable
fun SettingsScreen(onNavigateToOnboarding: () -> Unit) {
    val viewModel: SettingsScreenViewModel = hiltViewModel()
    val togglesWithState: List<FeatureToggleWithState> by viewModel.featureToggleStates.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigateToOnboarding.collectLatest {
            onNavigateToOnboarding()
        }
    }

    SettingsScreenContent(
        togglesWithState = togglesWithState,
        onFeatureToggleChanged = { id, enabled -> viewModel.onFeatureToggleChanged(id, enabled) },
        onResetAppButtonClicked = {
            viewModel.resetApp()
        },
    )
}

@Composable
private fun SettingsScreenContent(
    togglesWithState: List<FeatureToggleWithState>,
    onFeatureToggleChanged: (FeatureToggle, Boolean) -> Unit,
    onResetAppButtonClicked: () -> Unit,
) {
    MgoScaffold(
        appBarTitle = stringResource(CopyR.string.settings_heading),
        scrollable = false,
        contentPadding = PaddingValues(16.dp),
        content = {
            Column {
                togglesWithState.forEachIndexed { index, toggleWithState ->
                    FeatureToggleListItem(
                        modifier = Modifier.padding(bottom = 16.dp),
                        featureToggleWithState = toggleWithState,
                        onCheckedChange = { checked -> onFeatureToggleChanged(toggleWithState.featureToggle, checked) },
                    )
                }
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
                featureToggles.mapIndexed { index, toggle ->
                    FeatureToggleWithState(toggle, index == 1)
                },
            onFeatureToggleChanged = { _, _ -> },
            onResetAppButtonClicked = {},
        )
    }
}
