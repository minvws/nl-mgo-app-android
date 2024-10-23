package nl.rijksoverheid.mgo.feature.pincode.biometric

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.pincode.showBiometricPrompt
import nl.rijksoverheid.mgo.component.theme.ColumnWithButtons
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.headingMedium
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun PinCodeBioMetricSetupScreen(onNavigateToDashboard: () -> Unit) {
    val viewModel: PinCodeBiometricSetupScreenViewModel = hiltViewModel()
    PinCodeBioMetricSetupScreenContent(
        onBiometricLoginSuccess = {
            viewModel.setBiometricLoginEnabled()
            onNavigateToDashboard()
        },
        onNavigateToDashboard = onNavigateToDashboard,
    )
}

@Composable
private fun PinCodeBioMetricSetupScreenContent(
    onBiometricLoginSuccess: () -> Unit,
    onNavigateToDashboard: () -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "") })
        },
        content = { innerPadding ->
            ColumnWithButtons(
                modifier = Modifier.padding(innerPadding),
                buttonText = stringResource(id = CopyR.string.biometric_setup_enable),
                secondaryButtonText = stringResource(id = CopyR.string.common_skip),
                onButtonClick = {
                    coroutineScope.launch {
                        val fragmentActivity = context as FragmentActivity
                        val success = fragmentActivity.showBiometricPrompt()
                        if (success) {
                            onBiometricLoginSuccess()
                        }
                    }
                },
                onSecondaryButtonClick = { onNavigateToDashboard() },
            ) {
                Image(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.illustration_biometric),
                    contentDescription = null,
                )

                Text(
                    modifier = Modifier.padding(top = 32.dp),
                    text = stringResource(CopyR.string.biometric_setup_heading),
                    style = MaterialTheme.typography.headingMedium,
                )

                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(id = CopyR.string.biometric_setup_subheading),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        },
    )
}

@DefaultPreviews
@Composable
private fun PinCodeBiometricSetupScreenPreview() {
    MgoTheme {
        PinCodeBioMetricSetupScreenContent(
            onBiometricLoginSuccess = {},
            onNavigateToDashboard = {},
        )
    }
}
