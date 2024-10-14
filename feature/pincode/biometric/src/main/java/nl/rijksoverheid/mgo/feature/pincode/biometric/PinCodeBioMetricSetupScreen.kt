package nl.rijksoverheid.mgo.feature.pincode.biometric

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.ColumnWithButtons
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.headingMedium
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun PinCodeBioMetricSetupScreen(onNavigateToDashboard: () -> Unit) {
    PinCodeBioMetricSetupScreenContent(
        onNavigateToDashboard = onNavigateToDashboard,
    )
}

@Composable
private fun PinCodeBioMetricSetupScreenContent(onNavigateToDashboard: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "") }, backgroundColor = Color.Transparent, elevation = 0.dp)
        },
        content = { innerPadding ->
            ColumnWithButtons(
                modifier = Modifier.padding(innerPadding),
                buttonText = stringResource(id = CopyR.string.biometric_setup_enable),
                secondaryButtonText = stringResource(id = CopyR.string.common_skip),
                onButtonClick = { onNavigateToDashboard() },
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
            onNavigateToDashboard = {},
        )
    }
}
