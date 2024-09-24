package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.theme.ColumnWithButtons
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.actionTertiaryNegative
import nl.rijksoverheid.mgo.component.theme.bodySmall
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.headingLarge
import nl.rijksoverheid.mgo.component.theme.headingSmall
import nl.rijksoverheid.mgo.component.theme.supportApotheek
import nl.rijksoverheid.mgo.component.theme.supportFysiotherapeut
import nl.rijksoverheid.mgo.component.theme.supportGgz
import nl.rijksoverheid.mgo.component.theme.supportHuisarts
import nl.rijksoverheid.mgo.component.theme.supportKliniek
import nl.rijksoverheid.mgo.component.theme.supportTandarts
import nl.rijksoverheid.mgo.component.theme.supportThuiszorg
import nl.rijksoverheid.mgo.component.theme.supportVerpleeghuis
import nl.rijksoverheid.mgo.component.theme.supportZiekenhuis
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.getTitle
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem.HealthCategoriesListItem
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun HealthCategoriesScreen(
    arguments: HealthCategoriesScreenArguments,
    onNavigateBack: () -> Unit,
    onNavigateRemoveOrganization: (organization: MgoOrganization) -> Unit,
    onNavigateToLocalisation: () -> Unit,
    onNavigateToHealthCategory: (category: HealthCareCategory, organization: MgoOrganization?) -> Unit,
) {
    val viewModel =
        hiltViewModel<HealthCategoriesScreenViewModel, HealthCategoriesScreenViewModel.Factory>(
            creationCallback = { factory -> factory.create(arguments) },
        )
    val viewState: HealthCategoriesScreenViewState by viewModel.viewState.collectAsStateWithLifecycle()
    HealthCategoriesScreenContent(
        viewState = viewState,
        onNavigateBack = onNavigateBack,
        onClickAddProvider = onNavigateToLocalisation,
        onClickListItem = { category -> onNavigateToHealthCategory(category, arguments.filterOrganization) },
        onClickRemoveOrganization = onNavigateRemoveOrganization,
    )
}

@Composable
private fun HealthCategoriesScreenContent(
    viewState: HealthCategoriesScreenViewState,
    onNavigateBack: () -> Unit,
    onClickListItem: (category: HealthCareCategory) -> Unit,
    onClickAddProvider: () -> Unit,
    onClickRemoveOrganization: (organization: MgoOrganization) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    if (viewState.filterOrganization != null) {
                        IconButton(onClick = { onNavigateBack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = stringResource(id = CopyR.string.common_previous),
                            )
                        }
                    }
                },
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = viewState.getToolbarTitle(),
                    style = MaterialTheme.typography.headingLarge,
                )
                if (viewState.providers.isEmpty()) {
                    NoProviders(onClickAddProvider)
                } else {
                    WithProviders(
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                        filterOrganization = viewState.filterOrganization,
                        onClickListItem = onClickListItem,
                        onClickRemoveOrganization = onClickRemoveOrganization,
                    )
                }
            }
        },
    )
}

@Composable
private fun NoProviders(
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
private fun WithProviders(
    onClickListItem: (category: HealthCareCategory) -> Unit,
    filterOrganization: MgoOrganization?,
    onClickRemoveOrganization: (organization: MgoOrganization) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Card {
            Column {
                HealthCategoriesListItem(
                    modifier = Modifier.clickable { onClickListItem(HealthCareCategory.MEDICATIONS) },
                    icon = R.drawable.ic_medication,
                    title = HealthCareCategory.MEDICATIONS.getTitle(),
                    iconColor = MaterialTheme.colors.supportHuisarts(),
                    category = HealthCareCategory.MEDICATIONS,
                    filterOrganization = filterOrganization,
                )
                HealthCategoriesListItem(
                    modifier = Modifier.clickable { onClickListItem(HealthCareCategory.ALLERGIES) },
                    icon = R.drawable.ic_allergies,
                    iconColor = MaterialTheme.colors.supportKliniek(),
                    title = HealthCareCategory.ALLERGIES.getTitle(),
                    category = HealthCareCategory.ALLERGIES,
                    filterOrganization = filterOrganization,
                )
                HealthCategoriesListItem(
                    modifier = Modifier.clickable { onClickListItem(HealthCareCategory.MEASUREMENTS) },
                    icon = R.drawable.ic_measurements,
                    title = HealthCareCategory.MEASUREMENTS.getTitle(),
                    iconColor = MaterialTheme.colors.supportApotheek(),
                    category = HealthCareCategory.MEASUREMENTS,
                    filterOrganization = filterOrganization,
                )
                HealthCategoriesListItem(
                    modifier = Modifier.clickable { onClickListItem(HealthCareCategory.VACCINATIONS) },
                    icon = R.drawable.ic_vaccinations,
                    iconColor = MaterialTheme.colors.supportTandarts(),
                    title = HealthCareCategory.VACCINATIONS.getTitle(),
                    hasDivider = false,
                    category = HealthCareCategory.VACCINATIONS,
                    filterOrganization = filterOrganization,
                )
            }
        }

        Card(modifier = Modifier.padding(top = 16.dp)) {
            Column {
                HealthCategoriesListItem(
                    modifier = Modifier.clickable { onClickListItem(HealthCareCategory.COMPLAINTS) },
                    icon = R.drawable.ic_complaints,
                    iconColor = MaterialTheme.colors.supportVerpleeghuis(),
                    title = HealthCareCategory.COMPLAINTS.getTitle(),
                    filterOrganization = filterOrganization,
                    category = HealthCareCategory.COMPLAINTS,
                )
                HealthCategoriesListItem(
                    modifier = Modifier.clickable { onClickListItem(HealthCareCategory.TREATMENTS) },
                    icon = R.drawable.ic_treatments,
                    iconColor = MaterialTheme.colors.supportGgz(),
                    title = HealthCareCategory.TREATMENTS.getTitle(),
                    category = HealthCareCategory.TREATMENTS,
                    filterOrganization = filterOrganization,
                )
                HealthCategoriesListItem(
                    modifier = Modifier.clickable { onClickListItem(HealthCareCategory.LABRESULTS) },
                    icon = R.drawable.ic_labresults,
                    iconColor = MaterialTheme.colors.supportZiekenhuis(),
                    title = HealthCareCategory.LABRESULTS.getTitle(),
                    category = HealthCareCategory.LABRESULTS,
                    filterOrganization = filterOrganization,
                    hasDivider = false,
                )
            }
        }

        Card(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
            Column {
                HealthCategoriesListItem(
                    modifier = Modifier.clickable { onClickListItem(HealthCareCategory.REPORTS) },
                    icon = R.drawable.ic_reports,
                    iconColor = MaterialTheme.colors.supportFysiotherapeut(),
                    category = HealthCareCategory.REPORTS,
                    title = HealthCareCategory.REPORTS.getTitle(),
                    filterOrganization = filterOrganization,
                )
                HealthCategoriesListItem(
                    modifier = Modifier.clickable { onClickListItem(HealthCareCategory.DOCUMENTS) },
                    icon = R.drawable.ic_documents,
                    iconColor = MaterialTheme.colors.supportThuiszorg(),
                    category = HealthCareCategory.DOCUMENTS,
                    title = HealthCareCategory.DOCUMENTS.getTitle(),
                    filterOrganization = filterOrganization,
                )
            }
        }

        if (filterOrganization != null) {
            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { onClickRemoveOrganization(filterOrganization) },
                content = {
                    Text(
                        text = stringResource(id = CopyR.string.health_categories_remove_organization),
                        color = MaterialTheme.colors.actionTertiaryNegative(),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                    )
                },
            )
        }
    }
}

@DefaultPreviews
@Composable
internal fun OverviewScreenNoProvidersPreview() {
    MgoTheme {
        HealthCategoriesScreenContent(
            viewState = HealthCategoriesScreenViewState(name = "", filterOrganization = null, providers = listOf()),
            onNavigateBack = {},
            onClickAddProvider = {},
            onClickListItem = {},
            onClickRemoveOrganization = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun OverviewScreenWithProvidersPreview() {
    MgoTheme {
        HealthCategoriesScreenContent(
            viewState =
                HealthCategoriesScreenViewState(
                    name = "",
                    filterOrganization = null,
                    providers = listOf(TEST_MGO_ORGANIZATION),
                ),
            onNavigateBack = {},
            onClickAddProvider = {},
            onClickListItem = {},
            onClickRemoveOrganization = {},
        )
    }
}
