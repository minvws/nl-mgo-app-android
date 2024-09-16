package nl.rijksoverheid.mgo.feature.dashboard.organizations

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
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
import nl.rijksoverheid.mgo.component.theme.iconsSecondary
import nl.rijksoverheid.mgo.component.theme.strokesPrimary
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.feature.organizations.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

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
                modifier = Modifier.padding(innerPadding),
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(id = CopyR.string.healthcare_organizations_heading),
                    style = MaterialTheme.typography.headingLarge,
                )
                if (viewState.organizations.isEmpty()) {
                    NoOrganizations(onClickAddProvider)
                } else {
                    WithOrganizations(
                        modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp),
                        organizations = viewState.organizations,
                        onClickOrganization = onClickOrganization,
                        onClickAddProvider = onClickAddProvider,
                    )
                }
            }
        },
    )
}

@Composable
private fun NoOrganizations(
    onClickAddProvider: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ColumnWithButtons(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        buttonText = stringResource(id = CopyR.string.overview_add_organization),
        onButtonClick = onClickAddProvider,
    ) {
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
private fun WithOrganizations(
    organizations: List<MgoOrganization>,
    onClickOrganization: (organization: MgoOrganization) -> Unit,
    onClickAddProvider: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Card {
            Column(modifier = Modifier.fillMaxWidth()) {
                organizations.forEachIndexed { index, organization ->
                    OrganizationCard(
                        modifier = Modifier.fillMaxWidth().clickable { onClickOrganization(organization) },
                        organization = organization,
                        hasDivider = index != organizations.lastIndex,
                    )
                }
            }
        }

        Card(
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
                Text(text = stringResource(id = CopyR.string.overview_add_organization), style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    modifier = Modifier.padding(start = 8.dp),
                    painter = painterResource(id = R.drawable.ic_add_organization),
                    tint = MaterialTheme.colors.iconsSecondary(),
                    contentDescription = null,
                )
            }
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
                color = MaterialTheme.colors.strokesPrimary(),
            )
        }
    }
}

@DefaultPreviews
@Composable
internal fun OrganizationsScreenNoOrganizationsPreview() {
    MgoTheme {
        OrganizationsScreenContent(
            viewState = OrganizationsViewState(organizations = listOf()),
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
                ),
            onClickOrganization = {},
            onClickAddProvider = {},
        )
    }
}
