package nl.rijksoverheid.mgo.feature.pincode.confirm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.pincode.PinCodeWithKeyboard
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.headingLarge
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun PinCodeConfirmScreen(
    pinCodeToMatch: List<Int>,
    onPinConfirmed: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    val viewModel =
        hiltViewModel<PinCodeConfirmScreenViewModel, PinCodeConfirmScreenViewModel.Factory>(
            creationCallback = { factory -> factory.create(pinCodeToMatch) },
        )
    LaunchedEffect(Unit) {
        viewModel.resetPinCode()
        viewModel.navigateToDashboard.collectLatest {
            onPinConfirmed()
        }
    }
    val viewState by viewModel.viewState.collectAsState()
    PinCodeConfirmScreenContent(
        viewState = viewState,
        onAddPinCodeNumber = { number ->
            viewModel.addPinCodeNumber(number)
        },
        onPinErrorAnimationFinished = {
            viewModel.resetPinCode()
        },
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun PinCodeConfirmScreenContent(
    viewState: PinCodeConfirmScreenViewState,
    onAddPinCodeNumber: (number: Int) -> Unit,
    onPinErrorAnimationFinished: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
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
                val subHeadingText =
                    if (viewState.error) {
                        CopyR.string.pincode_confirm_mismatch
                    } else {
                        CopyR.string.pincode_confirm_subheading
                    }
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(id = subHeadingText),
                    style = MaterialTheme.typography.bodySmall,
                )
                PinCodeWithKeyboard(
                    modifier = Modifier.fillMaxSize(),
                    pinCode = viewState.pinCode,
                    error = viewState.error,
                    onPressNumber = onAddPinCodeNumber,
                    onErrorAnimationFinished = onPinErrorAnimationFinished,
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
                    pinCode = listOf(1, 2, 3),
                    subHeading = CopyR.string.pincode_confirm_heading,
                    error = false,
                ),
            onAddPinCodeNumber = {},
            onPinErrorAnimationFinished = {},
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
                    pinCode = listOf(1, 2, 3),
                    subHeading = CopyR.string.pincode_confirm_mismatch,
                    error = true,
                ),
            onAddPinCodeNumber = {},
            onPinErrorAnimationFinished = {},
            onNavigateBack = {},
        )
    }
}
