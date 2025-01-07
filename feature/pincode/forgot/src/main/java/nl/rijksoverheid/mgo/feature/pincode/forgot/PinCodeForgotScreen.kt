package nl.rijksoverheid.mgo.feature.pincode.forgot

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R
import kotlinx.coroutines.flow.collectLatest
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun PinCodeForgotScreen(
    onNavigateToPinCodeCreate: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    val viewModel: PinCodeForgotScreenViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.navigateToPinCodeCreate.collectLatest {
            onNavigateToPinCodeCreate()
        }
    }
    PinCodeForgotScreenContent(
        onCreateNewAccount = { viewModel.createNewAccount() },
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun PinCodeForgotScreenContent(
    onCreateNewAccount: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        nl.rijksoverheid.mgo.component.mgo.MgoAlertDialog(
            title = stringResource(id = R.string.forgot_pincode_dialog_heading),
            text = stringResource(id = R.string.forgot_pincode_dialog_subheading),
            onDismissRequest = { showDialog = false },
            confirmButtonText = stringResource(id = R.string.common_yes),
            onClickConfirmButton = {
                onCreateNewAccount()
                showDialog = false
            },
            dismissButtonText = stringResource(id = R.string.common_no),
            onClickDismissButton = {
                showDialog = false
            },
        )
    }

    nl.rijksoverheid.mgo.component.mgo.MgoScaffold(
        appBarTitle = stringResource(CopyR.string.forgot_pincode_heading),
        scrollStateProvider =
            nl.rijksoverheid.mgo.component.mgo.MgoScaffoldScrollStateProvider.Column(
                rememberScrollState(),
            ),
        onNavigateBack = onNavigateBack,
        primaryButtonText = stringResource(id = CopyR.string.common_cancel),
        onPrimaryButtonClick = onNavigateBack,
        secondaryButtonText = stringResource(id = CopyR.string.forgot_pincode_button),
        onSecondaryButtonClick = { showDialog = true },
        content = {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(id = CopyR.string.forgot_pincode_subheading),
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(16.dp))
        },
    )
}

@DefaultPreviews
@Composable
internal fun PinCodeForgotScreenPreview() {
    MgoTheme {
        PinCodeForgotScreenContent(
            onCreateNewAccount = {},
            onNavigateBack = {},
        )
    }
}
