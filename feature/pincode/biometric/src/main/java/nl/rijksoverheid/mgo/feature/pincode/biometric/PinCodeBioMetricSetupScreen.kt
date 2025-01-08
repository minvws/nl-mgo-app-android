package nl.rijksoverheid.mgo.feature.pincode.biometric

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.pincode.showBiometricPrompt
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.headingMedium
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

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
    nl.rijksoverheid.mgo.component.mgo.MgoScaffold(
        scrollStateProvider =
            nl.rijksoverheid.mgo.component.mgo.MgoScaffoldScrollStateProvider.Column(
                rememberScrollState(),
            ),
        primaryButtonText = stringResource(id = CopyR.string.biometric_setup_enable),
        onPrimaryButtonClick = {
            val fragmentActivity = context as FragmentActivity
            fragmentActivity.showBiometricPrompt(
                onSuccess = onBiometricLoginSuccess,
            )
        },
        secondaryButtonText = stringResource(id = CopyR.string.common_skip),
        onSecondaryButtonClick = onClickSkip,
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

        Spacer(modifier = Modifier.height(16.dp))
    }
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
