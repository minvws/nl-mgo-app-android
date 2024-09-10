package nl.rijksoverheid.mgo.feature.dashboard.overview

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
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
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.feature.dashboard.overview.listItem.OverviewListItem
import nl.rijksoverheid.mgo.feature.overview.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

const val TEST_TAG_OVERVIEW_ORGANIZATION_CARD = "OVERVIEW_ORGANIZATION_CARD"

@Composable
fun OverviewScreen(
    onNavigateToLocalisation: () -> Unit,
    onNavigateToMedications: () -> Unit,
) {
    val viewModel: OverviewScreenViewModel = hiltViewModel()
    val viewState: OverviewScreenViewState by viewModel.viewState.collectAsStateWithLifecycle()
    OverviewScreenContent(
        viewState = viewState,
        onClickAddProvider = onNavigateToLocalisation,
        onClickMedications = onNavigateToMedications,
    )
}

@Composable
private fun OverviewScreenContent(
    viewState: OverviewScreenViewState,
    onClickMedications: () -> Unit,
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
                    text = stringResource(id = CopyR.string.overview_heading),
                    style = MaterialTheme.typography.headingLarge,
                )
                if (viewState.providers.isEmpty()) {
                    NoProviders(onClickAddProvider)
                } else {
                    WithProviders(
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                        onClickMedications = onClickMedications,
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
    onClickMedications: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Card {
            Column {
                OverviewListItem(
                    icon = R.drawable.ic_medication,
                    title = CopyR.string.health_category_medication,
                    iconColor = MaterialTheme.colors.supportHuisarts(),
                    category = HealthCareCategory.MEDICATIONS,
                    onClickWhenLoaded = { onClickMedications() },
                )
                OverviewListItem(
                    icon = R.drawable.ic_allergies,
                    iconColor = MaterialTheme.colors.supportKliniek(),
                    title = CopyR.string.health_category_allergies,
                    category = HealthCareCategory.ALLERGIES,
                    onClickWhenLoaded = {},
                )
                OverviewListItem(
                    icon = R.drawable.ic_measurements,
                    title = CopyR.string.health_category_measurements,
                    iconColor = MaterialTheme.colors.supportApotheek(),
                    category = HealthCareCategory.MEASUREMENTS,
                    onClickWhenLoaded = {},
                )
                OverviewListItem(
                    icon = R.drawable.ic_vaccinations,
                    iconColor = MaterialTheme.colors.supportTandarts(),
                    title = CopyR.string.health_category_vaccinations,
                    hasDivider = false,
                    category = HealthCareCategory.VACCINATIONS,
                    onClickWhenLoaded = {},
                )
            }
        }

        Card(modifier = Modifier.padding(top = 16.dp)) {
            Column {
                OverviewListItem(
                    icon = R.drawable.ic_complaints,
                    iconColor = MaterialTheme.colors.supportVerpleeghuis(),
                    title = CopyR.string.health_category_complaints,
                    category = HealthCareCategory.COMPLAINTS,
                    onClickWhenLoaded = {},
                )
                OverviewListItem(
                    icon = R.drawable.ic_treatments,
                    iconColor = MaterialTheme.colors.supportGgz(),
                    title = CopyR.string.health_category_treatments,
                    category = HealthCareCategory.TREATMENTS,
                    onClickWhenLoaded = {},
                )
                OverviewListItem(
                    icon = R.drawable.ic_labresults,
                    iconColor = MaterialTheme.colors.supportZiekenhuis(),
                    title = CopyR.string.health_category_labresults,
                    category = HealthCareCategory.LABRESULTS,
                    hasDivider = false,
                    onClickWhenLoaded = {},
                )
            }
        }

        Card(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)) {
            Column {
                OverviewListItem(
                    icon = R.drawable.ic_reports,
                    iconColor = MaterialTheme.colors.supportFysiotherapeut(),
                    category = HealthCareCategory.REPORTS,
                    title = CopyR.string.health_category_reports,
                    onClickWhenLoaded = {},
                )
                OverviewListItem(
                    icon = R.drawable.ic_documents,
                    iconColor = MaterialTheme.colors.supportThuiszorg(),
                    category = HealthCareCategory.DOCUMENTS,
                    title = CopyR.string.health_category_documents,
                    onClickWhenLoaded = {},
                )
            }
        }
    }
}

@DefaultPreviews
@Composable
internal fun OverviewScreenNoProvidersPreview() {
    MgoTheme {
        OverviewScreenContent(
            viewState = OverviewScreenViewState(name = "", providers = listOf()),
            onClickAddProvider = {},
            onClickMedications = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun OverviewScreenWithProvidersPreview() {
    MgoTheme {
        OverviewScreenContent(
            viewState = OverviewScreenViewState(name = "", providers = listOf(TEST_MGO_ORGANIZATION)),
            onClickAddProvider = {},
            onClickMedications = {},
        )
    }
}
