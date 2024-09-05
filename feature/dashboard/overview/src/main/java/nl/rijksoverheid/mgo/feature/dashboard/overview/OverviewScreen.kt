package nl.rijksoverheid.mgo.feature.dashboard.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.theme.ColumnWithButtons
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.component.theme.headingSmall
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.feature.overview.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

const val TEST_TAG_OVERVIEW_ORGANIZATION_CARD = "OVERVIEW_ORGANIZATION_CARD"

@Composable
fun OverviewScreen(
    onNavigateToLocalisation: () -> Unit,
    onNavigateToOrganization: (provider: MgoOrganization) -> Unit,
) {
    val viewModel: OverviewScreenViewModel = hiltViewModel()
    val viewState: OverviewScreenViewState by viewModel.viewState.collectAsStateWithLifecycle()
    OverviewScreenContent(viewState = viewState, onNavigateToLocalisation = onNavigateToLocalisation)
}

@Composable
private fun OverviewScreenContent(
    viewState: OverviewScreenViewState,
    onNavigateToLocalisation: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
            )
        },
        content = { innerPadding ->
            Column(
                modifier =
                    Modifier
                        .padding(innerPadding),
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(id = CopyR.string.overview_heading),
                    style = MaterialTheme.typography.headingLarge,
                )
                if (viewState.providers.isEmpty()) {
                    NoProviders(onNavigateToLocalisation)
                } else {
                    WithProviders()
                }
            }
        },
    )
}

@Composable
private fun ColumnScope.NoProviders(onNavigateToLocalisation: () -> Unit) {
    ColumnWithButtons(
        contentPadding = PaddingValues(horizontal = 16.dp),
        buttonText = stringResource(id = CopyR.string.overview_add_organization),
        onButtonClick = onNavigateToLocalisation,
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(156.dp),
            painter = painterResource(id = R.drawable.illustration_overview_empty),
            contentDescription = null,
        )
        Text(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
            text = stringResource(id = CopyR.string.overview_empty_heading),
            style = MaterialTheme.typography.headingSmall,
            textAlign = TextAlign.Center,
        )
        Text(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            text = stringResource(id = CopyR.string.overview_empty_subheading),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colors.contentTertiary(),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun ColumnScope.WithProviders() {
    Text(text = "Hello World")
}

@DefaultPreviews
@Composable
internal fun OverviewScreenPreview() {
    MgoTheme {
        OverviewScreenContent(
            viewState = OverviewScreenViewState(name = "", providers = listOf()),
            onNavigateToLocalisation = {},
        )
    }
}
