package nl.rijksoverheid.mgo.feature.settings.about.home.versions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoTopAppBar
import nl.rijksoverheid.mgo.component.theme.LabelsPrimary
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R

@Composable
fun SettingsHomeVersionsScreen(onNavigateBack: () -> Unit) {
  val viewModel: SettingsHomeVersionsScreenViewModel = hiltViewModel()
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()
  SettingsHomeVersionsScreenContent(viewState = viewState, onNavigateBack = onNavigateBack)
}

@Composable
private fun SettingsHomeVersionsScreenContent(
  viewState: SettingsHomeVersionsScreenViewState,
  onNavigateBack: () -> Unit,
) {
  Scaffold(
    topBar = {
      MgoTopAppBar(
        title = stringResource(R.string.settings_about_this_app_version),
        onNavigateBack = onNavigateBack,
      )
    },
  ) { contentPadding ->
    Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(contentPadding).padding(16.dp)) {
      Text(
        text = "HCIM Package",
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.LabelsPrimary(),
      )

      MgoCard(modifier = Modifier.padding(top = 12.dp)) {
        ListItem(
          heading = "Versie",
          subHeading = viewState.hcimPackageVersion ?: "-",
        )
        ListItem(
          heading = "Datum",
          subHeading = viewState.hcimPackageDate ?: "-",
        )
        ListItem(
          heading = "Git-ref",
          subHeading = viewState.hcimPackageGitRef ?: "-",
          hasDivider = false,
        )
      }

      Text(
        modifier = Modifier.padding(top = 32.dp),
        text = "Health Categories Config",
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.LabelsPrimary(),
      )

      MgoCard(modifier = Modifier.padding(top = 12.dp)) {
        ListItem(
          heading = "Versie",
          subHeading = viewState.healthCategoriesConfigVersion ?: "-",
        )
        ListItem(
          heading = "Datum",
          subHeading = viewState.healthCategoriesConfigDate ?: "-",
        )
        ListItem(
          heading = "Git-ref",
          subHeading = viewState.healthCategoriesConfigGitRef ?: "-",
          hasDivider = false,
        )
      }

      Text(
        modifier = Modifier.padding(top = 32.dp),
        text = "Patient Friendly Terms",
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.LabelsPrimary(),
      )

      MgoCard(modifier = Modifier.padding(top = 12.dp)) {
        ListItem(
          heading = "ETag",
          subHeading = viewState.patientFriendlyTermsETag ?: "-",
          hasDivider = false,
        )
      }
    }
  }
}

@Composable
private fun ListItem(
  heading: String,
  subHeading: String,
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
          text = heading,
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.LabelsSecondary(),
        )
        Text(
          text = subHeading,
          style = MaterialTheme.typography.bodyMedium,
          fontWeight = FontWeight.Normal,
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

@Composable
@PreviewLightDark
internal fun SettingsHomeVersionsScreenPreview() {
  MgoTheme {
    SettingsHomeVersionsScreenContent(
      viewState =
        SettingsHomeVersionsScreenViewState(
          hcimPackageVersion = "1.4.2",
          hcimPackageDate = "8 september 2025",
          hcimPackageGitRef = "abc123def",
          healthCategoriesConfigVersion = "2.0.7",
          healthCategoriesConfigDate = "9 september 2025",
          healthCategoriesConfigGitRef = "9f8e7d6c",
          patientFriendlyTermsETag = "686897696a7c876b7e",
        ),
      onNavigateBack = {},
    )
  }
}
