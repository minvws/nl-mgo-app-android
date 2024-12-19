package nl.rijksoverheid.mgo.feature.digid

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.composable.MgoCard
import nl.rijksoverheid.mgo.component.theme.composable.MgoScaffold
import nl.rijksoverheid.mgo.component.theme.composable.MgoScaffoldScrollStateProvider
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun DigidLoginScreen(onNavigateToDigidMock: () -> Unit) {
    MgoScaffold(
        appBarTitle = stringResource(id = CopyR.string.login_heading),
        scrollStateProvider = MgoScaffoldScrollStateProvider.Column(rememberScrollState()),
    ) {
        Text(
            text = stringResource(id = CopyR.string.login_subheading),
            style = MaterialTheme.typography.bodySmall,
        )

        MgoCard(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
        ) {
            Row(
                modifier =
                    Modifier
                        .clickable { onNavigateToDigidMock() }
                        .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(modifier = Modifier.size(32.dp), painter = painterResource(R.drawable.ic_digid), contentDescription = null)
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(CopyR.string.login_digid),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@DefaultPreviews
@Composable
internal fun DigidLoginScreenPreview() {
    MgoTheme {
        DigidLoginScreen(
            onNavigateToDigidMock = {},
        )
    }
}
