package nl.rijksoverheid.mgo.feature.settings.advanced

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoTopAppBar
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.framework.environment.featureToggle.FeatureToggle
import nl.rijksoverheid.mgo.framework.environment.featureToggle.FeatureToggleEntry

@Composable
fun SettingsAdvancedScreen(onNavigateBack: () -> Unit) {
  val viewModel = hiltViewModel<SettingsAdvancedScreenViewModel>()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  SettingsAdvancedScreenContent(
    viewState = viewState,
    onClickListItem = { entry, enabled ->
      viewModel.setToggle(entry.toggle, enabled)
    },
    onClickBack = onNavigateBack,
  )
}

@Composable
private fun SettingsAdvancedScreenContent(
  viewState: SettingsAdvancedScreenViewState,
  onClickListItem: (toggle: FeatureToggleEntry<*>, enabled: Boolean) -> Unit,
  onClickBack: () -> Unit,
) {
  Scaffold(
    topBar = {
      MgoTopAppBar(
        title = stringResource(R.string.settings_advanced_heading),
        onNavigateBack = onClickBack,
      )
    },
    content = { contentPadding ->
      Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(contentPadding).padding(16.dp)) {
        for (featureToggle in viewState.featureToggles) {
          SettingsAdvancedListItem(
            modifier = Modifier.padding(bottom = 8.dp),
            heading = featureToggle.toggle.name,
            subheading = featureToggle.toggle.description,
            enabled = featureToggle.value as Boolean,
            onClick = { value ->
              onClickListItem(featureToggle, value)
            },
          )
        }
      }
    },
  )
}

@Composable
private fun SettingsAdvancedListItem(
  heading: String,
  subheading: String,
  enabled: Boolean,
  onClick: (enabled: Boolean) -> Unit,
  modifier: Modifier = Modifier,
) {
  MgoCard(modifier = modifier) {
    Row(
      modifier =
        Modifier
          .clickable { onClick(!enabled) }
          .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = heading,
          style = MaterialTheme.typography.bodyMedium,
        )
        Text(
          modifier = Modifier.padding(top = 4.dp),
          text = subheading,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.LabelsSecondary(),
        )
      }

      Switch(checked = enabled, onCheckedChange = onClick)
    }
  }
}

@DefaultPreviews
@Composable
internal fun SettingsAdvancedScreenPreview() {
  MgoTheme {
    SettingsAdvancedScreenContent(
      viewState =
        SettingsAdvancedScreenViewState(
          featureToggles =
            listOf(
              FeatureToggleEntry(
                toggle = FeatureToggle<Boolean>(id = "1", name = "Feature toggle #1", description = "This is the first feature toggle", initialValue = true),
                true,
              ),
              FeatureToggleEntry(
                toggle = FeatureToggle<Boolean>(id = "2", name = "Feature toggle #2", description = "This is the second feature toggle", initialValue = false),
                false,
              ),
            ),
        ),
      onClickListItem = { _, _ -> },
      onClickBack = {},
    )
  }
}
