package nl.rijksoverheid.mgo.feature.settings.display

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import nl.rijksoverheid.mgo.component.theme.SymbolsPrimary
import nl.rijksoverheid.mgo.component.theme.getIcon
import nl.rijksoverheid.mgo.component.theme.theme.AppTheme
import nl.rijksoverheid.mgo.framework.copy.R

@Composable
fun SettingsDisplayScreen(onNavigateBack: () -> Unit) {
  val viewModel = hiltViewModel<SettingsDisplayScreenViewModel>()
  val selectedTheme by viewModel.appTheme.collectAsStateWithLifecycle()

  SettingsDisplayScreenContent(
    selectedTheme = selectedTheme,
    onSelectTheme = { theme ->
      viewModel.setTheme(theme)
    },
    onClickBack = onNavigateBack,
  )
}

@Composable
private fun SettingsDisplayScreenContent(
  selectedTheme: AppTheme,
  onSelectTheme: (theme: AppTheme) -> Unit,
  onClickBack: () -> Unit,
) {
  Scaffold(
    topBar = {
      MgoTopAppBar(
        title = stringResource(R.string.settings_display_heading),
        onNavigateBack = onClickBack,
      )
    },
    content = { contentPadding ->
      Column(modifier = Modifier.verticalScroll(rememberScrollState()).padding(contentPadding).padding(16.dp)) {
        MgoCard(
          modifier =
            Modifier
              .padding(top = 8.dp),
        ) {
          ThemeListItem(
            modifier =
              Modifier
                .fillMaxWidth(),
            theme = AppTheme.SYSTEM,
            onSelectTheme = {
              onSelectTheme(AppTheme.SYSTEM)
            },
            selected = selectedTheme == AppTheme.SYSTEM,
            hasDivider = true,
          )
          ThemeListItem(
            modifier =
              Modifier
                .fillMaxWidth(),
            theme = AppTheme.LIGHT,
            onSelectTheme = {
              onSelectTheme(AppTheme.LIGHT)
            },
            selected = selectedTheme == AppTheme.LIGHT,
            hasDivider = true,
          )
          ThemeListItem(
            modifier =
              Modifier
                .fillMaxWidth(),
            theme = AppTheme.DARK,
            onSelectTheme = {
              onSelectTheme(AppTheme.DARK)
            },
            selected = selectedTheme == AppTheme.DARK,
            hasDivider = false,
          )
        }
      }
    },
  )
}

@Composable
private fun ThemeListItem(
  theme: AppTheme,
  onSelectTheme: () -> Unit,
  selected: Boolean,
  hasDivider: Boolean = true,
  modifier: Modifier = Modifier,
) {
  Column {
    Row(
      modifier =
        modifier
          .clickable { onSelectTheme() }
          .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Icon(
        imageVector = theme.getIcon(),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.SymbolsPrimary(),
      )

      Column(
        modifier =
          Modifier
            .weight(1f)
            .padding(start = 16.dp),
      ) {
        Text(
          text = stringResource(theme.getHeading()),
          style = MaterialTheme.typography.bodyMedium,
        )

        theme.getSubHeading()?.let { subHeading ->
          Text(
            text = stringResource(subHeading),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.LabelsSecondary(),
          )
        }
      }

      RadioButton(
        selected = selected,
        onClick = { onSelectTheme() },
      )
    }
    if (hasDivider) {
      HorizontalDivider(
        modifier = Modifier.padding(start = 56.dp),
      )
    }
  }
}

@StringRes
private fun AppTheme.getHeading(): Int =
  when (this) {
    AppTheme.SYSTEM -> R.string.settings_display_system_heading
    AppTheme.LIGHT -> R.string.settings_display_light
    AppTheme.DARK -> R.string.settings_display_dark
  }

@StringRes
private fun AppTheme.getSubHeading(): Int? {
  if (this == AppTheme.SYSTEM) {
    return R.string.settings_display_system_subheading
  }
  return null
}

@DefaultPreviews
@Composable
internal fun SettingsDisplayScreenPreview() {
  MgoTheme {
    SettingsDisplayScreenContent(
      selectedTheme = AppTheme.SYSTEM,
      onSelectTheme = {},
      onClickBack = {},
    )
  }
}
