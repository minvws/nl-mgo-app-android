package nl.rijksoverheid.mgo.feature.digid

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.mgo.MgoHtmlText
import nl.rijksoverheid.mgo.component.mgo.MgoScaffold
import nl.rijksoverheid.mgo.component.mgo.MgoScaffoldScrollStateProvider
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a temporary screen that indicates that the login process with DigiD has been completed.
 * This is for demo purposes to show a nice screen instead of the test environment from DigiD.
 *
 * @param onNavigateToLocalisation Called when requested to navigate to start of searching for health care providers.
 */
@Composable
fun DigidMockScreen(onNavigateToLocalisation: () -> Unit) {
    MgoScaffold(
        scrollStateProvider =
            MgoScaffoldScrollStateProvider.Column(
                rememberScrollState(),
            ),
        primaryButtonText = stringResource(id = CopyR.string.common_next),
        onPrimaryButtonClick = onNavigateToLocalisation,
    ) {
        Image(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = TopAppBarDefaults.MediumAppBarCollapsedHeight)
                    .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.illustration_mock),
            contentDescription = null,
        )

        Text(
            modifier = Modifier.padding(top = 32.dp),
            text = stringResource(id = CopyR.string.login_info_heading),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )

        MgoHtmlText(
            modifier = Modifier.padding(top = 16.dp),
            html = stringResource(id = CopyR.string.login_info_subheading),
            style = MaterialTheme.typography.bodySmall,
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@DefaultPreviews
@Composable
internal fun DigidMockScreenPreview() {
    MgoTheme {
        DigidMockScreen(
            onNavigateToLocalisation = {},
        )
    }
}
