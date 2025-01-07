package nl.rijksoverheid.mgo.feature.pincode.login

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.pincode.PinCodeWithKeyboard
import nl.rijksoverheid.mgo.component.pincode.showBiometricPrompt
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

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
            val fragmentActivity = context as FragmentActivity
            fragmentActivity.showBiometricPrompt(
                onSuccess = onBiometricLoginSuccess,
            )
        }
    }

    nl.rijksoverheid.mgo.component.mgo.MgoScaffold(
        appBarTitle = stringResource(id = CopyR.string.pincode_validation_heading),
        appBarTitleAlign = TextAlign.Center,
        scrollStateProvider =
            nl.rijksoverheid.mgo.component.mgo.MgoScaffoldScrollStateProvider.Column(
                rememberScrollState(),
            ),
        content = {
            Text(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .focusRequester(subHeadingFocusRequester)
                        .focusable(),
                text = stringResource(id = CopyR.string.pincode_confirm_subheading),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
            )
            PinCodeWithKeyboard(
                modifier = Modifier.fillMaxSize(),
                onPinCodeEntered = onPinCodeEntered,
                onResetError = onResetError,
                error = if (viewState.error) stringResource(id = CopyR.string.pincode_validation_wrong) else null,
                hint = stringResource(id = CopyR.string.pincode_forgot),
                onClickHint = onNavigateForgotPin,
                hasBiometric = viewState.hasBiometric,
                onPressBiometric = {
                    val fragmentActivity = context as FragmentActivity
                    fragmentActivity.showBiometricPrompt(
                        onSuccess = onBiometricLoginSuccess,
                    )
                },
            )
        },
    )
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
