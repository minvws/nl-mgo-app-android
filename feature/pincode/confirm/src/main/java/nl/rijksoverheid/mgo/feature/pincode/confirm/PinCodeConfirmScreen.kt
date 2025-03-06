package nl.rijksoverheid.mgo.feature.pincode.confirm

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.mgo.MgoScaffold
import nl.rijksoverheid.mgo.component.mgo.MgoScaffoldScrollStateProvider
import nl.rijksoverheid.mgo.component.pincode.PinCodeWithKeyboard
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a screen where you can enter your pin code again so it's validated with another entered pin code.
 *
 * @param pinCodeToMatch The pin code entered in a previous screen that should match the pin code entered here.
 * @param onNavigate Called when requested to navigate to another screen.
 * @param onNavigateBack Called when requested to navigate back.
 */
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
    MgoScaffold(
        appBarTitle = stringResource(id = CopyR.string.pincode_confirm_heading),
        scrollStateProvider =
            MgoScaffoldScrollStateProvider.Column(
                rememberScrollState(),
            ),
        onNavigateBack = onNavigateBack,
        content = {
            Text(
                modifier = Modifier.focusRequester(subHeadingFocusRequester).focusable().height(getSubHeadingTextHeight()),
                text = stringResource(id = CopyR.string.pincode_confirm_subheading),
                style = MaterialTheme.typography.bodySmall,
            )
            PinCodeWithKeyboard(
                modifier = Modifier.weight(1f),
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
                error = if (viewState.error) stringResource(CopyR.string.pincode_confirm_mismatch) else null,
            )
        },
    )
}

/**
 * Measure how big the top text was in the previous screen. We want this text to be the same height so that the pin code
 * does not move.
 * @return The height the sub text needs to be.
 */
@Composable
private fun getSubHeadingTextHeight(): Dp {
    val textMeasurer = rememberTextMeasurer()
    val constraintsWidth = with(LocalDensity.current) { (LocalConfiguration.current.screenWidthDp.dp - 16.dp).roundToPx() }
    val measuredLayoutResult =
        textMeasurer.measure(
            constraints = Constraints(maxWidth = constraintsWidth),
            text = stringResource(id = CopyR.string.pincode_create_subheading),
            style = MaterialTheme.typography.bodySmall,
        )
    return with(LocalDensity.current) { measuredLayoutResult.size.height.toDp() }
}

@DefaultPreviews
@Composable
internal fun PinCodeConfirmScreenPreview() {
    MgoTheme {
        PinCodeConfirmScreenContent(
            viewState =
                PinCodeConfirmScreenViewState(
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
                    error = true,
                ),
            onPinCodeEntered = {},
            onResetError = {},
            onNavigateBack = {},
        )
    }
}
