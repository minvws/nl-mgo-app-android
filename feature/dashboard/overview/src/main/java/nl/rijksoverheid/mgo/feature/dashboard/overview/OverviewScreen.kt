package nl.rijksoverheid.mgo.feature.dashboard.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
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
import nl.rijksoverheid.mgo.component.theme.composable.MgoCard
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.component.theme.iconsSecondary
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
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
    OverviewScreenContent(
        viewState = viewState,
        onNavigateToLocalisation = onNavigateToLocalisation,
        onNavigateToOrganization = onNavigateToOrganization,
    )
}

@Composable
private fun OverviewScreenContent(
    viewState: OverviewScreenViewState,
    onNavigateToLocalisation: () -> Unit,
    onNavigateToOrganization: (provider: MgoOrganization) -> Unit,
) {
    Scaffold { innerPadding ->
        ColumnWithButtons(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(bottom = 16.dp),
            buttonText =
                stringResource(
                    id = CopyR.string.overview_add_organization,
                ),
            onButtonClick =
            onNavigateToLocalisation,
        ) {
            val hasProviders = viewState.providers.isNotEmpty()
            Header(modifier = Modifier.padding(top = 32.dp), hasProviders = hasProviders)
            Spacer(modifier = Modifier.padding(top = 24.dp))

            if (hasProviders) {
                viewState.providers.fastForEachIndexed { _, provider ->
                    OrganizationCard(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp)
                                .clickable {
                                    onNavigateToOrganization(provider)
                                },
                        provider = provider,
                    )
                }
            } else {
                EmptyState(modifier = Modifier.padding(top = 24.dp))
            }
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    hasProviders: Boolean,
) {
    Row(modifier = modifier.padding(horizontal = 16.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stringResource(id = CopyR.string.overview_heading),
                style = MaterialTheme.typography.headingLarge,
            )
            val subtitleResource =
                if (hasProviders) CopyR.string.overview_subheading else CopyR.string.overview_no_organizations_found
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = stringResource(id = subtitleResource),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colors.contentTertiary(),
            )
        }
        // TODO No user information yet so don't display avatar
        // Avatar()
    }
}

@Composable
private fun OrganizationCard(
    modifier: Modifier = Modifier,
    provider: MgoOrganization,
) {
    MgoCard(modifier = modifier.testTag(TEST_TAG_OVERVIEW_ORGANIZATION_CARD)) {
        Column(modifier = Modifier.padding(16.dp)) {
            val category = provider.category ?: stringResource(id = CopyR.string.common_unknown)
            Text(text = provider.name, style = MaterialTheme.typography.bodyDefault, fontWeight = FontWeight.Bold)
            Text(modifier = Modifier.padding(top = 4.dp), text = category, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Image(
        modifier =
            modifier
                .fillMaxWidth()
                .height(300.dp),
        painter = painterResource(id = R.drawable.illustration_overview_empty),
        contentDescription = null,
    )
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
internal fun OverviewScreenWithProvidersPreview() {
    MgoTheme {
        OverviewScreenContent(
            viewState =
                OverviewScreenViewState(
                    name = "mevrouw de Bruijn",
                    providers =
                        listOf(
                            TEST_MGO_ORGANIZATION,
                            TEST_MGO_ORGANIZATION,
                            TEST_MGO_ORGANIZATION,
                        ),
                ),
            onNavigateToLocalisation = {},
            onNavigateToOrganization = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun OverviewScreenEmptyStatePreview() {
    MgoTheme {
        OverviewScreenContent(
            viewState =
                OverviewScreenViewState(
                    name = "mevrouw de Bruijn",
                    providers = listOf(),
                ),
            onNavigateToLocalisation = {},
            onNavigateToOrganization = {},
        )
    }
}
