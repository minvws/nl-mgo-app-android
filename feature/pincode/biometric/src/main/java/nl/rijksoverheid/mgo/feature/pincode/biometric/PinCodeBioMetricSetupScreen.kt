package nl.rijksoverheid.mgo.feature.pincode.biometric

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButton
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtons
import nl.rijksoverheid.mgo.component.pincode.showBiometricPrompt
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a screen where you can authenticate via biometrics.
 *
 * @param onNavigateToDigid Called when requested to navigate to the digid authentication screen.
 */
@Composable
fun PinCodeBioMetricSetupScreen(onNavigateToDigid: () -> Unit) {
  val viewModel: PinCodeBiometricSetupScreenViewModel = hiltViewModel()
  PinCodeBioMetricSetupScreenContent(
    onBiometricLoginSuccess = {
      viewModel.setBiometricLoginEnabled()
      onNavigateToDigid()
    },
    onClickSkip = onNavigateToDigid,
  )
}

@Composable
private fun PinCodeBioMetricSetupScreenContent(
  onBiometricLoginSuccess: () -> Unit,
  onClickSkip: () -> Unit,
) {
  val context = LocalContext.current
  val scrollState = rememberScrollState()
  Scaffold(
    content = { contentPadding ->
      Column(modifier = Modifier.padding(contentPadding)) {
        Column(
          modifier =
            Modifier
              .weight(1f)
              .verticalScroll(scrollState)
              .padding(16.dp),
        ) {
          Image(
            modifier =
              Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(top = TopAppBarDefaults.LargeAppBarCollapsedHeight),
            painter = painterResource(id = R.drawable.illustration_biometric),
            contentDescription = null,
          )

          Text(
            text = stringResource(CopyR.string.biometric_setup_heading),
            style = MaterialTheme.typography.headlineLarge,
          )

          Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = CopyR.string.biometric_setup_subheading),
            style = MaterialTheme.typography.bodyMedium,
          )
        }

        MgoBottomButtons(
          primaryButton =
            MgoBottomButton(
              text = stringResource(id = CopyR.string.biometric_setup_enable),
              onClick = {
                val fragmentActivity = context as FragmentActivity
                fragmentActivity.showBiometricPrompt(
                  onSuccess = onBiometricLoginSuccess,
                )
              },
            ),
          secondaryButton =
            MgoBottomButton(
              text = stringResource(id = CopyR.string.common_skip),
              onClick = onClickSkip,
            ),
          isElevated = scrollState.canScrollForward,
        )
      }
    },
  )
}

@DefaultPreviews
@Composable
internal fun PinCodeBiometricSetupScreenPreview() {
  MgoTheme {
    PinCodeBioMetricSetupScreenContent(
      onBiometricLoginSuccess = {},
      onClickSkip = {},
    )
  }
}
