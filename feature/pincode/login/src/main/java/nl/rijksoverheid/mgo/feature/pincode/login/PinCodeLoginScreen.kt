package nl.rijksoverheid.mgo.feature.pincode.login

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.component.mgo.MgoAutoScrollColumn
import nl.rijksoverheid.mgo.component.mgo.MgoLargeTopAppBar
import nl.rijksoverheid.mgo.component.pincode.PinCodeWithKeyboard
import nl.rijksoverheid.mgo.component.pincode.showBiometricPrompt
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a screen where you can enter a pin code that is validated against a saved pin code.
 *
 * @param onNavigateForgotPin Called when requested to navigate to the screen where you can reset the pin code.
 * @param onPinValidated Called when the pin code has been successfully validated.
 */
@Composable
fun PinCodeLoginScreen(
  onNavigateForgotPin: () -> Unit,
  onPinValidated: () -> Unit,
) {
  val viewModel: PinCodeLoginScreenViewModel = hiltViewModel()
  LaunchedEffect(Unit) {
    viewModel.navigateToDashboard.collectLatest {
      onPinValidated()
    }
  }
  val viewState by viewModel.viewState.collectAsStateWithLifecycle()
  PinCodeLoginScreenContent(
    viewState = viewState,
    onBiometricLoginSuccess = {
      onPinValidated()
    },
    onPinCodeEntered = { pinCode ->
      viewModel.validatePinCode(pinCode)
    },
    onResetError = {
      viewModel.resetError()
    },
    onNavigateForgotPin = onNavigateForgotPin,
  )
}

@Composable
private fun PinCodeLoginScreenContent(
  viewState: PinCodeLoginScreenViewState,
  onBiometricLoginSuccess: () -> Unit,
  onPinCodeEntered: (pinCode: List<Int>) -> Unit,
  onResetError: () -> Unit,
  onNavigateForgotPin: () -> Unit,
) {
  val context = LocalContext.current
  val subHeadingFocusRequester = remember { FocusRequester() }

  // Immediately show the biometric prompt if it has been enabled in the onboarding before
  LaunchedEffect(Unit) {
    if (viewState.hasBiometric) {
      val fragmentActivity = context.findFragmentActivity()
      fragmentActivity.showBiometricPrompt(
        onSuccess = onBiometricLoginSuccess,
      )
    }
  }

  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
  Scaffold(
    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    topBar = {
      MgoLargeTopAppBar(
        title = stringResource(id = CopyR.string.pincode_validation_heading),
        scrollBehavior = scrollBehavior,
        textAlign = TextAlign.Center,
      )
    },
    content = { contentPadding ->
      MgoAutoScrollColumn(modifier = Modifier.padding(contentPadding).padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Text(
          modifier =
            Modifier
              .fillMaxWidth()
              .focusRequester(subHeadingFocusRequester)
              .focusable(),
          text = stringResource(id = CopyR.string.pincode_validation_subheading),
          textAlign = TextAlign.Center,
          style = MaterialTheme.typography.bodyMedium,
        )
        PinCodeWithKeyboard(
          onPinCodeEntered = onPinCodeEntered,
          onResetError = onResetError,
          error = if (viewState.error) stringResource(id = CopyR.string.pincode_validation_wrong) else null,
          hint = stringResource(id = CopyR.string.pincode_forgot),
          onClickHint = onNavigateForgotPin,
          hasBiometric = viewState.hasBiometric,
          onPressBiometric = {
            val fragmentActivity = context.findFragmentActivity()
            fragmentActivity.showBiometricPrompt(
              onSuccess = onBiometricLoginSuccess,
            )
          },
        )
      }
    },
  )
}

private fun Context.findFragmentActivity(): FragmentActivity {
  if (this is FragmentActivity) {
    return this
  }

  // Sometimes when resuming the app or after configuration changes, Compose reattaches the composition tree
  // to a new ContextThemeWrapper. We can get the activity in the base context.
  val fragmentActivity = ((this as ContextThemeWrapper).baseContext).findFragmentActivity()
  return fragmentActivity
}

@DefaultPreviews
@Composable
internal fun PinCodeLoginScreenPreview() {
  MgoTheme {
    PinCodeLoginScreenContent(
      viewState =
        PinCodeLoginScreenViewState(
          hasBiometric = true,
          error = false,
        ),
      onBiometricLoginSuccess = {},
      onPinCodeEntered = {},
      onResetError = {},
      onNavigateForgotPin = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun PinCodeLoginScreenErrorPreview() {
  MgoTheme {
    PinCodeLoginScreenContent(
      viewState =
        PinCodeLoginScreenViewState(
          hasBiometric = true,
          error = true,
        ),
      onBiometricLoginSuccess = {},
      onPinCodeEntered = {},
      onResetError = {},
      onNavigateForgotPin = {},
    )
  }
}

@DefaultPreviews
@Composable
internal fun PinCodeLoginWithoutBiometricScreenPreview() {
  MgoTheme {
    PinCodeLoginScreenContent(
      viewState =
        PinCodeLoginScreenViewState(
          hasBiometric = false,
          error = false,
        ),
      onBiometricLoginSuccess = {},
      onPinCodeEntered = {},
      onResetError = {},
      onNavigateForgotPin = {},
    )
  }
}
