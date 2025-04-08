package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.rijksoverheid.mgo.component.mgo.MgoButton
import nl.rijksoverheid.mgo.component.mgo.MgoButtonTheme
import nl.rijksoverheid.mgo.component.mgo.MgoCard
import nl.rijksoverheid.mgo.component.mgo.MgoScaffold
import nl.rijksoverheid.mgo.component.mgo.MgoScaffoldScrollStateProvider
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.contentSecondary
import nl.rijksoverheid.mgo.component.theme.supportAllergies
import nl.rijksoverheid.mgo.component.theme.supportContacts
import nl.rijksoverheid.mgo.component.theme.supportDevice
import nl.rijksoverheid.mgo.component.theme.supportDocuments
import nl.rijksoverheid.mgo.component.theme.supportFunctional
import nl.rijksoverheid.mgo.component.theme.supportLaboratory
import nl.rijksoverheid.mgo.component.theme.supportLifestyle
import nl.rijksoverheid.mgo.component.theme.supportMedication
import nl.rijksoverheid.mgo.component.theme.supportPayer
import nl.rijksoverheid.mgo.component.theme.supportPersonal
import nl.rijksoverheid.mgo.component.theme.supportProblems
import nl.rijksoverheid.mgo.component.theme.supportProcedures
import nl.rijksoverheid.mgo.component.theme.supportTreatment
import nl.rijksoverheid.mgo.component.theme.supportVaccinations
import nl.rijksoverheid.mgo.component.theme.supportVitals
import nl.rijksoverheid.mgo.component.theme.supportWarning
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem.HealthCategoriesListItem
import nl.rijksoverheid.mgo.framework.util.getStringResourceByName
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * Composable that shows a screen with a list of health categories. These health categories are populated with either health cara data
 * for all added health providers, or just a single one.
 *
 * @param appBarTitle The title of the app bar.
 * @param subHeading Text under the app bar that contains a small explanation about this screen.
 * @param onNavigateRemoveOrganization Called when requested to navigate to the screen where you can remove an organization.
 * @param onNavigateToLocalisation Called when requested to navigate to the screen where you can search for organizations.
 * @param onNavigateToHealthCategory Called when requested to navigate to the screen where you can view health care data.
 * @param organization If not null, will only show only health care data for this organization. If null will show for all added
 * organizations.
 * @param onNavigateBack Called when requested to navigate back.
 */
@Composable
fun HealthCategoriesScreen(
    appBarTitle: String,
    subHeading: String,
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
        subHeading = subHeading,
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
    subHeading: String,
    viewState: HealthCategoriesScreenViewState,
    onClickListItem: (category: HealthCareCategory) -> Unit,
    onClickAddProvider: () -> Unit,
    onClickRemoveOrganization: (organization: MgoOrganization) -> Unit,
    organization: MgoOrganization? = null,
    onNavigateBack: (() -> Unit)? = null,
) {
    val primaryButtonText =
        when {
            viewState.providers.isEmpty() -> {
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
        appBarTitle = appBarTitle,
        scrollStateProvider =
            MgoScaffoldScrollStateProvider.Column(
                rememberScrollState(),
            ),
        primaryButtonText = primaryButtonText,
        onPrimaryButtonClick = onClickAddProvider,
        onNavigateBack = onNavigateBack,
        content = {
            if (viewState.providers.isEmpty()) {
                NoProviders()
            } else {
                WithProviders(
                    subHeading = subHeading,
                    filterOrganization = organization,
                    onClickListItem = onClickListItem,
                    onClickRemoveOrganization = onClickRemoveOrganization,
                )
            }
        },
    )
}

@Composable
private fun ColumnScope.NoProviders() {
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
        text = stringResource(id = CopyR.string.common_no_organizations_heading),
        style = MaterialTheme.typography.headlineSmall,
        textAlign = TextAlign.Center,
    )
    Text(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        text = stringResource(id = CopyR.string.common_no_organizations_subheading),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.contentSecondary(),
        textAlign = TextAlign.Center,
    )
    Spacer(modifier = Modifier.height(16.dp))
    Spacer(modifier = Modifier.weight(1f))
}

@Composable
private fun ColumnScope.WithProviders(
    subHeading: String,
    onClickListItem: (category: HealthCareCategory) -> Unit,
    filterOrganization: MgoOrganization?,
    onClickRemoveOrganization: (organization: MgoOrganization) -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = subHeading,
        style = MaterialTheme.typography.bodyMedium,
    )
    MgoCard(
        modifier =
            modifier.padding(
                top = 8.dp,
                bottom = 16.dp,
            ),
    ) {
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
            modifier =
                Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally),
            buttonText = stringResource(id = CopyR.string.organizations_remove_organization),
            onClick = {
                onClickRemoveOrganization(filterOrganization)
            },
            buttonTheme = MgoButtonTheme.TERTIARY_NEGATIVE,
        )
    }
}

@Composable
@StringRes
private fun HealthCareCategory.getTitle(): Int {
    val stringResource = LocalContext.current.getStringResourceByName("hc_$id.heading")
    if (stringResource == 0) {
        return CopyR.string.common_unknown
    }
    return stringResource
}

@DrawableRes
private fun HealthCareCategory.getIcon(): Int {
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
private fun HealthCareCategory.getIconColor(): Color {
    return when (this) {
        HealthCareCategory.MEDICATIONS -> MaterialTheme.colorScheme.supportMedication()
        HealthCareCategory.MEASUREMENTS -> MaterialTheme.colorScheme.supportVitals()
        HealthCareCategory.LAB_RESULTS -> MaterialTheme.colorScheme.supportLaboratory()
        HealthCareCategory.ALLERGIES -> MaterialTheme.colorScheme.supportAllergies()
        HealthCareCategory.TREATMENTS -> MaterialTheme.colorScheme.supportTreatment()
        HealthCareCategory.APPOINTMENTS -> MaterialTheme.colorScheme.supportContacts()
        HealthCareCategory.VACCINATIONS -> MaterialTheme.colorScheme.supportVaccinations()
        HealthCareCategory.DOCUMENTS -> MaterialTheme.colorScheme.supportDocuments()
        HealthCareCategory.COMPLAINTS -> MaterialTheme.colorScheme.supportProblems()
        HealthCareCategory.PATIENT -> MaterialTheme.colorScheme.supportPersonal()
        HealthCareCategory.ALERTS -> MaterialTheme.colorScheme.supportWarning()
        HealthCareCategory.PAYMENT -> MaterialTheme.colorScheme.supportPayer()
        HealthCareCategory.PLANS -> MaterialTheme.colorScheme.supportProcedures()
        HealthCareCategory.DEVICES -> MaterialTheme.colorScheme.supportDevice()
        HealthCareCategory.MENTAL -> MaterialTheme.colorScheme.supportFunctional()
        HealthCareCategory.LIFESTYLE -> MaterialTheme.colorScheme.supportLifestyle()
    }
}

@DefaultPreviews
@Composable
internal fun OverviewScreenNoProvidersPreview() {
    MgoTheme {
        HealthCategoriesScreenContent(
            appBarTitle = stringResource(CopyR.string.overview_heading),
            subHeading = stringResource(CopyR.string.overview_subheading),
            viewState = HealthCategoriesScreenViewState(name = "", providers = listOf(), automaticLocalisationEnabled = false),
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
            appBarTitle = stringResource(CopyR.string.overview_heading),
            subHeading = stringResource(CopyR.string.overview_subheading),
            viewState =
                HealthCategoriesScreenViewState(
                    name = "",
                    providers = listOf(TEST_MGO_ORGANIZATION),
                    automaticLocalisationEnabled = false,
                ),
            onNavigateBack = {},
            onClickAddProvider = {},
            onClickListItem = {},
            onClickRemoveOrganization = {},
        )
    }
}
