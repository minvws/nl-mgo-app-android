package nl.rijksoverheid.mgo.feature.settings.security

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoTopAppBar
import nl.rijksoverheid.mgo.component.pincode.showBiometricPrompt
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.LabelsSecondary
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.SymbolsPrimary
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a screen where you can change different security settings regarding the app.
 *
 * @param onNavigateBack Called when requested to navigate back.
 */
@Composable
fun SettingsSecurityScreen(onNavigateBack: () -> Unit) {
  val viewModel = hiltViewModel<SettingsSecurityScreenViewModel>()
  val biometricEnabled by viewModel.biometricEnabled.collectAsStateWithLifecycle()

  SettingsSecurityScreenContent(
    biometricEnabled = biometricEnabled,
    onEnableBiometric = { enabled ->
      viewModel.setBiometricEnabled(enabled)
    },
    onClickBack = onNavigateBack,
  )
}

@Composable
private fun SettingsSecurityScreenContent(
  biometricEnabled: Boolean,
  onEnableBiometric: (enabled: Boolean) -> Unit,
  onClickBack: () -> Unit,
) {
  val context = LocalContext.current

  Scaffold(
    topBar = {
      MgoTopAppBar(
        title = stringResource(CopyR.string.settings_accessibility_heading),
        onNavigateBack = onClickBack,
      )
    },
    content = { contentPadding ->
      Column(
        modifier = Modifier.verticalScroll(rememberScrollState()).padding(contentPadding).padding(16.dp),
      ) {
        MgoCard {
          Row(
            modifier =
              Modifier
                .clickable { onEnableBiometric(!biometricEnabled) }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
          ) {
            Icon(
              imageVector = Icons.Outlined.Fingerprint,
              contentDescription = null,
              tint = MaterialTheme.colorScheme.SymbolsPrimary(),
            )

            Column(
              modifier =
                Modifier
                  .weight(1f)
                  .padding(horizontal = 16.dp),
            ) {
              Text(
                text = stringResource(CopyR.string.settings_security_biometric_heading),
                style = MaterialTheme.typography.bodyMedium,
              )

              Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(CopyR.string.settings_security_biometric_subheading),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.LabelsSecondary(),
              )
            }

            Switch(checked = biometricEnabled, onCheckedChange = { checked ->
              if (checked) {
                val fragmentActivity = context as FragmentActivity
                fragmentActivity.showBiometricPrompt(
                  onSuccess = {
                    onEnableBiometric(true)
                  },
                )
              } else {
                onEnableBiometric(false)
              }
            })
          }
        }
      }
    },
  )
}

@DefaultPreviews
@Composable
internal fun SettingsSecurityScreenPreview() {
  MgoTheme {
    SettingsSecurityScreenContent(
      biometricEnabled = true,
      onEnableBiometric = {},
      onClickBack = {},
    )
  }
}
