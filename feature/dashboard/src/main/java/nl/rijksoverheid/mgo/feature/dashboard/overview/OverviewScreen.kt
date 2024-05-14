package nl.rijksoverheid.mgo.feature.dashboard.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.theme.ColumnWithButtons
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.backgroundPrimary
import nl.rijksoverheid.mgo.component.theme.bodyDefault
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.headingMedium
import nl.rijksoverheid.mgo.component.theme.iconsSecondary
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
internal fun OverviewScreen(onNavigateToLocalisation: () -> Unit) {
    val viewModel: OverviewScreenViewModel = hiltViewModel()
    val viewState: OverviewScreenViewState by viewModel.viewState.collectAsStateWithLifecycle()

    Scaffold { innerPadding ->
        ColumnWithButtons(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(bottom = 16.dp),
            buttonText =
                stringResource(
                    id = CopyR.string.dashboard_overview_button,
                ),
            onButtonClick =
            onNavigateToLocalisation,
        ) {
            Header(modifier = Modifier.padding(top = 32.dp))
            Spacer(modifier = Modifier.padding(top = 24.dp))
            viewState.providers.fastForEachIndexed { _, provider ->
                HealthCareProviderCard(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                    provider = provider,
                )
            }
        }
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(horizontal = 16.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = CopyR.string.dashboard_overview_title),
                style = MaterialTheme.typography.headingMedium,
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = CopyR.string.dashboard_overview_description),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colors.contentTertiary(),
            )
        }
        Avatar()
    }
}

@Composable
private fun HealthCareProviderCard(
    modifier: Modifier = Modifier,
    provider: HealthCareProvider,
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            val category = provider.category ?: stringResource(id = CopyR.string.general_unknown)
            Text(text = provider.name, style = MaterialTheme.typography.bodyDefault, fontWeight = FontWeight.Bold)
            Text(modifier = Modifier.padding(top = 4.dp), text = category, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun Avatar() {
    val backgroundColor = MaterialTheme.colors.iconsSecondary()
    Text(
        modifier =
            Modifier
                .drawBehind {
                    drawCircle(
                        color = backgroundColor,
                        radius = this.size.maxDimension / 2f,
                    )
                }
                .padding(vertical = 12.dp, horizontal = 8.dp),
        text = "WB",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colors.backgroundPrimary(),
    )
}

@DefaultPreviews
@Composable
internal fun OverviewScreenPreview() {
    MgoTheme {
        OverviewScreen(
            onNavigateToLocalisation = {},
        )
    }
}
