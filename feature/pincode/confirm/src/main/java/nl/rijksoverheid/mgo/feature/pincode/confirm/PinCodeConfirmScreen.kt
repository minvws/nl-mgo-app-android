package nl.rijksoverheid.mgo.feature.pincode.confirm

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.pincode.PinCodeWithKeyboard
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.headingLarge
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun PinCodeConfirmScreen(
    pinCodeToMatch: List<Int>,
    onNavigate: (navigation: PinCodeConfirmScreenNextNavigation) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val viewModel =
        hiltViewModel<PinCodeConfirmScreenViewModel, PinCodeConfirmScreenViewModel.Factory>(
            creationCallback = { factory -> factory.create(pinCodeToMatch) },
        )
    LaunchedEffect(Unit) {
        viewModel.navigate.collectLatest { navigation ->
            onNavigate(navigation)
        }
    }
    val viewState by viewModel.viewState.collectAsState()
    PinCodeConfirmScreenContent(
        viewState = viewState,
        onPinCodeEntered = { pinCode ->
            viewModel.validatePinCode(pinCode)
        },
        onResetError = {
            viewModel.resetError()
        },
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun PinCodeConfirmScreenContent(
    viewState: PinCodeConfirmScreenViewState,
    onPinCodeEntered: (pinCode: List<Int>) -> Unit,
    onResetError: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val subHeadingFocusRequester = remember { FocusRequester() }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = stringResource(id = CopyR.string.common_previous),
                        )
                    }
                },
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
                    text = stringResource(id = CopyR.string.pincode_confirm_heading),
                    style = MaterialTheme.typography.headingLarge,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp).focusRequester(subHeadingFocusRequester).focusable(),
                    text = stringResource(id = viewState.subHeading),
                    style = MaterialTheme.typography.bodySmall,
                )
                PinCodeWithKeyboard(
                    modifier = Modifier.fillMaxSize(),
                    onPinCodeEntered = onPinCodeEntered,
                    onResetError = {
                        onResetError()
                        coroutineScope.launch {
                            // Seems to be a bug where if you request focus it only works once.
                            // Doing it like this fixes that.
                            focusManager.clearFocus()
                            delay(100)
                            subHeadingFocusRequester.requestFocus()
                        }
                    },
                    error = viewState.error,
                )
            }
        },
    )
}

@DefaultPreviews
@Composable
internal fun PinCodeConfirmScreenPreview() {
    MgoTheme {
        PinCodeConfirmScreenContent(
            viewState =
                PinCodeConfirmScreenViewState(
                    subHeading = CopyR.string.pincode_confirm_heading,
                    error = false,
                ),
            onPinCodeEntered = {},
            onResetError = {},
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun PinCodeConfirmScreenErrorPreview() {
    MgoTheme {
        PinCodeConfirmScreenContent(
            viewState =
                PinCodeConfirmScreenViewState(
                    subHeading = CopyR.string.pincode_confirm_mismatch,
                    error = true,
                ),
            onPinCodeEntered = {},
            onResetError = {},
            onNavigateBack = {},
        )
    }
}
