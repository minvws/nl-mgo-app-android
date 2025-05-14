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
import androidx.datastore.preferences.core.Preferences
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoTopAppBar
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_FLAG_SECURE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_SKIP_PIN

/**
 * Composable that shows a screen where developers can easily set different feature toggles for the app.
 *
 * @param onNavigateBack Called when requested to navigate back.
 */
@Composable
fun SettingsAdvancedScreen(onNavigateBack: () -> Unit) {
  val viewModel = hiltViewModel<SettingsAdvancedScreenViewModel>()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()

  SettingsAdvancedScreenContent(
    viewState = viewState,
    onClickListItem = { key, enabled ->
      viewModel.setToggle(key, enabled)
    },
    onClickBack = onNavigateBack,
  )
}

@Composable
private fun SettingsAdvancedScreenContent(
  viewState: SettingsAdvancedScreenViewState,
  onClickListItem: (key: Preferences.Key<Boolean>, enabled: Boolean) -> Unit,
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
        SettingsAdvancedListItem(
          title = "Automatische lokalisatie",
          enabled = viewState.automaticLocalisation,
          onClick = { enabled ->
            onClickListItem(KEY_AUTOMATIC_LOCALISATION, enabled)
          },
        )

        SettingsAdvancedListItem(
          modifier = Modifier.padding(top = 16.dp),
          title = "Flag secure",
          enabled = viewState.flagSecure,
          onClick = { enabled ->
            onClickListItem(KEY_FLAG_SECURE, enabled)
          },
        )

        SettingsAdvancedListItem(
          modifier = Modifier.padding(top = 16.dp),
          title = "Skip pin code",
          enabled = viewState.skipPinCode,
          onClick = { enabled ->
            onClickListItem(KEY_SKIP_PIN, enabled)
          },
        )
      }
    },
  )
}

@Composable
private fun SettingsAdvancedListItem(
  title: String,
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
      Text(
        modifier = Modifier.weight(1f),
        text = title,
        style = MaterialTheme.typography.bodyMedium,
      )

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
          automaticLocalisation = true,
          flagSecure = false,
          skipPinCode = true,
        ),
      onClickListItem = { _, _ -> },
      onClickBack = {},
    )
  }
}
