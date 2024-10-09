package nl.rijksoverheid.mgo.feature.pincode.create

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.pincode.PinCodeWithKeyboard
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.framework.copy.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun PinCodeCreateScreen(
    onPinEntered: (pinCode: List<Int>) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val viewModel: PinCodeCreateScreenViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.resetPinCode()
        viewModel.navigateToConfirm.collectLatest { pinCode ->
            onPinEntered(pinCode)
        }
    }
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()
    PinCodeCreateScreenContent(
        pinCode = viewState.pinCode,
        onAddPinCodeNumber = { number ->
            viewModel.addPinCodeNumber(number)
        },
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun PinCodeCreateScreenContent(
    pinCode: List<Int>,
    onAddPinCodeNumber: (number: Int) -> Unit,
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
                            contentDescription = stringResource(id = R.string.common_previous),
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
                    text = stringResource(id = R.string.pincode_create_heading),
                    style = MaterialTheme.typography.headingLarge,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(id = R.string.pincode_create_subheading),
                    style = MaterialTheme.typography.bodySmall,
                )
                PinCodeWithKeyboard(
                    modifier = Modifier.fillMaxSize(),
                    pinCode = pinCode,
                    onPressNumber = onAddPinCodeNumber,
                )
            }
        },
    )
}

@DefaultPreviews
@Composable
internal fun PinCodeCreateScreenPreview() {
    MgoTheme {
        PinCodeCreateScreenContent(
            pinCode = listOf(1, 2, 3),
            onAddPinCodeNumber = {},
            onNavigateBack = {},
        )
    }
}
