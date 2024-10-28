package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import nl.rijksoverheid.mgo.component.theme.composable.MgoButton
import nl.rijksoverheid.mgo.component.theme.composable.MgoButtonTheme
import nl.rijksoverheid.mgo.component.theme.composable.MgoCard
import nl.rijksoverheid.mgo.component.theme.composable.MgoScaffold
import nl.rijksoverheid.mgo.component.theme.contentTertiary
import nl.rijksoverheid.mgo.component.theme.headingSmall
import nl.rijksoverheid.mgo.component.theme.notificationInformation
import nl.rijksoverheid.mgo.component.theme.supportApotheek
import nl.rijksoverheid.mgo.component.theme.supportGegevens
import nl.rijksoverheid.mgo.component.theme.supportGgd
import nl.rijksoverheid.mgo.component.theme.supportGgz
import nl.rijksoverheid.mgo.component.theme.supportHuisarts
import nl.rijksoverheid.mgo.component.theme.supportKliniek
import nl.rijksoverheid.mgo.component.theme.supportOverige
import nl.rijksoverheid.mgo.component.theme.supportRevalidatie
import nl.rijksoverheid.mgo.component.theme.supportRijkslint
import nl.rijksoverheid.mgo.component.theme.supportRivm
import nl.rijksoverheid.mgo.component.theme.supportTandarts
import nl.rijksoverheid.mgo.component.theme.supportThuiszorg
import nl.rijksoverheid.mgo.component.theme.supportVerloskundige
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
    appBarTitle: String,
    onNavigateRemoveOrganization: (organization: MgoOrganization) -> Unit,
    onNavigateToLocalisation: () -> Unit,
    onNavigateToHealthCategory: (category: HealthCareCategory, organization: MgoOrganization?) -> Unit,
    organization: MgoOrganization? = null,
    onNavigateBack: (() -> Unit)? = null,
) {
    val viewModel = hiltViewModel<HealthCategoriesScreenViewModel>()
    val viewState: HealthCategoriesScreenViewState by viewModel.viewState.collectAsStateWithLifecycle()
    HealthCategoriesScreenContent(
        appBarTitle = appBarTitle,
        viewState = viewState,
        onNavigateBack = onNavigateBack,
        onClickAddProvider = onNavigateToLocalisation,
        onClickListItem = { category -> onNavigateToHealthCategory(category, organization) },
        onClickRemoveOrganization = onNavigateRemoveOrganization,
        organization = organization,
    )
}

@Composable
private fun HealthCategoriesScreenContent(
    appBarTitle: String,
    viewState: HealthCategoriesScreenViewState,
    onClickListItem: (category: HealthCareCategory) -> Unit,
    onClickAddProvider: () -> Unit,
    onClickRemoveOrganization: (organization: MgoOrganization) -> Unit,
    organization: MgoOrganization? = null,
    onNavigateBack: (() -> Unit)? = null,
) {
    MgoScaffold(
        appBarTitle = appBarTitle,
        scrollable = viewState.providers.isNotEmpty(),
        isRootScaffold = false,
        onNavigateBack = onNavigateBack,
        content = {
            if (viewState.providers.isEmpty()) {
                NoProviders(onClickAddProvider)
            } else {
                WithProviders(
                    filterOrganization = organization,
                    onClickListItem = onClickListItem,
                    onClickRemoveOrganization = onClickRemoveOrganization,
                )
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
            color = MaterialTheme.colorScheme.contentTertiary(),
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun ColumnScope.WithProviders(
    onClickListItem: (category: HealthCareCategory) -> Unit,
    filterOrganization: MgoOrganization?,
    onClickRemoveOrganization: (organization: MgoOrganization) -> Unit,
    modifier: Modifier = Modifier,
) {
    MgoCard(modifier = Modifier.padding(top = 2.dp)) {
        Column {
            HealthCareCategory.entries.forEach { category ->
                HealthCategoriesListItem(
                    modifier = Modifier.clickable { onClickListItem(category) },
                    icon = category.getIcon(),
                    title = category.getTitle(),
                    iconColor = category.getIconColor(),
                    category = category,
                    filterOrganization = filterOrganization,
                )
            }
        }
    }

    if (filterOrganization != null) {
        MgoButton(
            modifier = Modifier.padding(vertical = 16.dp).align(Alignment.CenterHorizontally),
            buttonText = stringResource(id = CopyR.string.health_categories_remove_organization),
            onClick = {
                onClickRemoveOrganization(filterOrganization)
            },
            buttonTheme = MgoButtonTheme.TERTIARY_NEGATIVE,
        )
    }
}

@DrawableRes
fun HealthCareCategory.getIcon(): Int {
    return when (this) {
        HealthCareCategory.MEDICATIONS -> R.drawable.ic_medication
        HealthCareCategory.MEASUREMENTS -> R.drawable.ic_measurements
        HealthCareCategory.LAB_RESULTS -> R.drawable.ic_labresults
        HealthCareCategory.ALLERGIES -> R.drawable.ic_allergies
        HealthCareCategory.TREATMENTS -> R.drawable.ic_treatments
        HealthCareCategory.APPOINTMENTS -> R.drawable.ic_appointments
        HealthCareCategory.VACCINATIONS -> R.drawable.ic_vaccinations
        HealthCareCategory.DOCUMENTS -> R.drawable.ic_documents
        HealthCareCategory.COMPLAINTS -> R.drawable.ic_complaints
        HealthCareCategory.PATIENT -> R.drawable.ic_patient
        HealthCareCategory.ALERTS -> R.drawable.ic_alerts
        HealthCareCategory.PAYMENT -> R.drawable.ic_payment
        HealthCareCategory.PLANS -> R.drawable.ic_plans
        HealthCareCategory.DEVICES -> R.drawable.ic_devices
        HealthCareCategory.MENTAL -> R.drawable.ic_mental
        HealthCareCategory.LIFESTYLE -> R.drawable.ic_lifestyle
    }
}

@Composable
fun HealthCareCategory.getIconColor(): Color {
    return when (this) {
        HealthCareCategory.MEDICATIONS -> MaterialTheme.colorScheme.supportHuisarts()
        HealthCareCategory.MEASUREMENTS -> MaterialTheme.colorScheme.supportApotheek()
        HealthCareCategory.LAB_RESULTS -> MaterialTheme.colorScheme.supportZiekenhuis()
        HealthCareCategory.ALLERGIES -> MaterialTheme.colorScheme.supportKliniek()
        HealthCareCategory.TREATMENTS -> MaterialTheme.colorScheme.supportGgz()
        HealthCareCategory.APPOINTMENTS -> MaterialTheme.colorScheme.supportGgd()
        HealthCareCategory.VACCINATIONS -> MaterialTheme.colorScheme.supportTandarts()
        HealthCareCategory.DOCUMENTS -> MaterialTheme.colorScheme.supportThuiszorg()
        HealthCareCategory.COMPLAINTS -> MaterialTheme.colorScheme.supportVerpleeghuis()
        HealthCareCategory.PATIENT -> MaterialTheme.colorScheme.supportOverige()
        HealthCareCategory.ALERTS -> MaterialTheme.colorScheme.supportRivm()
        HealthCareCategory.PAYMENT -> MaterialTheme.colorScheme.supportVerloskundige()
        HealthCareCategory.PLANS -> MaterialTheme.colorScheme.supportRevalidatie()
        HealthCareCategory.DEVICES -> MaterialTheme.colorScheme.supportRijkslint()
        HealthCareCategory.MENTAL -> MaterialTheme.colorScheme.notificationInformation()
        HealthCareCategory.LIFESTYLE -> MaterialTheme.colorScheme.supportGegevens()
    }
}

@DefaultPreviews
@Composable
internal fun OverviewScreenNoProvidersPreview() {
    MgoTheme {
        HealthCategoriesScreenContent(
            appBarTitle = stringResource(CopyR.string.overview_heading),
            viewState = HealthCategoriesScreenViewState(name = "", providers = listOf()),
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
            stringResource(CopyR.string.overview_heading),
            viewState =
                HealthCategoriesScreenViewState(
                    name = "",
                    providers = listOf(TEST_MGO_ORGANIZATION),
                ),
            onNavigateBack = {},
            onClickAddProvider = {},
            onClickListItem = {},
            onClickRemoveOrganization = {},
        )
    }
}
