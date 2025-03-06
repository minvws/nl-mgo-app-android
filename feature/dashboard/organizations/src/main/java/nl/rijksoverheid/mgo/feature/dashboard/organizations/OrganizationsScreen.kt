package nl.rijksoverheid.mgo.feature.dashboard.organizations

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoScaffold
import nl.rijksoverheid.mgo.component.mgo.MgoScaffoldScrollStateProvider
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.borderPrimary
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.component.theme.headingSmall
import nl.rijksoverheid.mgo.component.theme.symbolsSecondary
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.feature.organizations.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a screen with a list of added health care providers.
 *
 * @param onNavigateToHealthCategories Called when requested to navigate to the screen that shows health categories.
 * @param onNavigateToLocalisation Called when requested to navigate to the start of navigation where to search for health care providers.
 */
@Composable
fun OrganizationsScreen(
    onNavigateToHealthCategories: (organization: MgoOrganization) -> Unit,
    onNavigateToLocalisation: () -> Unit,
) {
    val viewModel: OrganizationsViewModel = hiltViewModel()
    val viewState: OrganizationsViewState by viewModel.viewState.collectAsStateWithLifecycle()
    OrganizationsScreenContent(
        viewState = viewState,
        onClickOrganization = onNavigateToHealthCategories,
        onClickAddProvider = onNavigateToLocalisation,
    )
}

@Composable
private fun OrganizationsScreenContent(
    viewState: OrganizationsViewState,
    onClickOrganization: (organization: MgoOrganization) -> Unit,
    onClickAddProvider: () -> Unit,
) {
    val primaryButtonText =
        when {
            viewState.organizations.isEmpty() -> {
                if (viewState.automaticLocalisationEnabled) {
                    stringResource(id = CopyR.string.common_search_organizations)
                } else {
                    stringResource(id = CopyR.string.common_add_organizations)
                }
            } else -> {
                null
            }
        }
    MgoScaffold(
        appBarTitle = stringResource(CopyR.string.organizations_heading),
        scrollStateProvider =
            MgoScaffoldScrollStateProvider.Column(
                rememberScrollState(),
            ),
        primaryButtonText = primaryButtonText,
        onPrimaryButtonClick = onClickAddProvider,
        content = {
            if (viewState.organizations.isEmpty()) {
                NoOrganizations()
            } else {
                WithOrganizations(
                    organizations = viewState.organizations,
                    onClickOrganization = onClickOrganization,
                    onClickAddProvider = onClickAddProvider,
                    automaticLocalisationEnabled = viewState.automaticLocalisationEnabled,
                )
            }
        },
    )
}

@Composable
private fun ColumnScope.NoOrganizations() {
    Spacer(modifier = Modifier.weight(1f))
    Image(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(156.dp),
        painter = painterResource(id = R.drawable.illustration_organizations_empty),
        contentDescription = null,
    )
    Text(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
        text = stringResource(id = CopyR.string.common_no_organizations_heading),
        style = MaterialTheme.typography.headingSmall,
        textAlign = TextAlign.Center,
    )
    Text(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        text = stringResource(id = CopyR.string.common_no_organizations_subheading),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.contentSecondary(),
        textAlign = TextAlign.Center,
    )
    Spacer(modifier = Modifier.height(16.dp))
    Spacer(modifier = Modifier.weight(1f))
}

@Composable
private fun WithOrganizations(
    organizations: List<MgoOrganization>,
    automaticLocalisationEnabled: Boolean,
    onClickOrganization: (organization: MgoOrganization) -> Unit,
    onClickAddProvider: () -> Unit,
) {
    MgoCard(modifier = Modifier.padding(top = 2.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            organizations.forEachIndexed { index, organization ->
                OrganizationCard(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { onClickOrganization(organization) },
                    organization = organization,
                    hasDivider = index != organizations.lastIndex,
                )
            }
        }
    }

    MgoCard(
        modifier =
            Modifier
                .padding(vertical = 16.dp)
                .clickable { onClickAddProvider() },
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        ) {
            val stringResource =
                if (automaticLocalisationEnabled) CopyR.string.common_search_organizations else CopyR.string.common_add_organizations
            Text(
                text = stringResource(id = stringResource),
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.padding(start = 8.dp),
                painter = painterResource(id = R.drawable.ic_add_organization),
                tint = MaterialTheme.colorScheme.symbolsSecondary(),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun OrganizationCard(
    organization: MgoOrganization,
    hasDivider: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = organization.name,
            style = MaterialTheme.typography.bodySmall,
        )
        if (hasDivider) {
            Divider(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(0.33.dp)
                        .padding(start = 16.dp),
                color = MaterialTheme.colorScheme.borderPrimary(),
            )
        }
    }
}

@DefaultPreviews
@Composable
internal fun OrganizationsScreenNoOrganizationsPreview() {
    MgoTheme {
        OrganizationsScreenContent(
            viewState = OrganizationsViewState(organizations = listOf(), automaticLocalisationEnabled = false),
            onClickOrganization = {},
            onClickAddProvider = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun OrganizationsScreenWithOrganizationsPreview() {
    MgoTheme {
        OrganizationsScreenContent(
            viewState =
                OrganizationsViewState(
                    organizations =
                        listOf(
                            TEST_MGO_ORGANIZATION.copy(name = "Streekziekenhuis Willem Alexander"),
                            TEST_MGO_ORGANIZATION.copy(name = "Huisartsenpraktijk De Haven"),
                            TEST_MGO_ORGANIZATION.copy(name = "Fysiotherapie Centrum"),
                            TEST_MGO_ORGANIZATION.copy(name = "Tandartsenpraktijk Tandje Erbij"),
                            TEST_MGO_ORGANIZATION.copy(name = "Apotheek de Pillendoos"),
                        ),
                    automaticLocalisationEnabled = false,
                ),
            onClickOrganization = {},
            onClickAddProvider = {},
        )
    }
}
