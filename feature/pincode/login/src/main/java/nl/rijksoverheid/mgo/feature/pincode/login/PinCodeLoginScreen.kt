package nl.rijksoverheid.mgo.feature.pincode.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.pincode.PinCodeWithKeyboard
import nl.rijksoverheid.mgo.component.pincode.showBiometricPrompt
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.framework.copy.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun PinCodeLoginScreen(onPinValidated: () -> Unit) {
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
    )
}

@Composable
private fun PinCodeLoginScreenContent(
    viewState: PinCodeLoginScreenViewState,
    onBiometricLoginSuccess: () -> Unit,
    onPinCodeEntered: (pinCode: List<Int>) -> Unit,
    onResetError: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
            )
        },
        content = { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .padding(16.dp)
                        .padding(innerPadding),
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.pincode_validation_heading),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headingLarge,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                    text = stringResource(id = viewState.subHeading),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                )
                PinCodeWithKeyboard(
                    modifier = Modifier.fillMaxSize(),
                    onPinCodeEntered = onPinCodeEntered,
                    onResetError = onResetError,
                    error = viewState.error,
                    hint = stringResource(id = R.string.pincode_forgot),
                    hasBiometric = viewState.hasBiometric,
                    onPressBiometric = {
                        coroutineScope.launch {
                            val fragmentActivity = context as FragmentActivity
                            val success = fragmentActivity.showBiometricPrompt()
                            if (success) {
                                onBiometricLoginSuccess()
                            }
                        }
                    },
                )
            }
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
                    subHeading = R.string.pincode_confirm_heading,
                    hasBiometric = true,
                    error = false,
                ),
            onBiometricLoginSuccess = {},
            onPinCodeEntered = {},
            onResetError = {},
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
                    subHeading = R.string.pincode_validation_wrong,
                    hasBiometric = true,
                    error = true,
                ),
            onBiometricLoginSuccess = {},
            onPinCodeEntered = {},
            onResetError = {},
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
                    subHeading = R.string.pincode_confirm_heading,
                    hasBiometric = false,
                    error = false,
                ),
            onBiometricLoginSuccess = {},
            onPinCodeEntered = {},
            onResetError = {},
        )
    }
}
