package nl.rijksoverheid.mgo.feature.healthcareprovider.medication

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import nl.rijksoverheid.mgo.component.collapsablecard.CollapsableCardItem
import nl.rijksoverheid.mgo.component.collapsablecard.CollapsableCardProperty
import nl.rijksoverheid.mgo.component.results.ResultsScreen
import nl.rijksoverheid.mgo.component.results.ResultsScreenViewState
import nl.rijksoverheid.mgo.component.theme.DefaultPreviews
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.data.medication.models.MgoMedication
import nl.rijksoverheid.mgo.data.medication.models.TEST_MGO_MEDICATION
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun MedicationScreen(
    providerName: String,
    onNavigateBack: () -> Unit,
) {
    val viewModel: MedicationScreenViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()
    MedicationScreenContent(
        providerName = providerName,
        viewState = viewState,
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun MedicationScreenContent(
    providerName: String,
    viewState: MedicationScreenViewState,
    onNavigateBack: () -> Unit,
) {
    ResultsScreen(
        heading = stringResource(id = CopyR.string.medication_use_heading),
        subHeading = stringResource(id = CopyR.string.medication_use_subheading, providerName),
        viewState = viewState.toResultsScreenViewState(),
        onNavigateBack = onNavigateBack,
    )
}

@DefaultPreviews
@Composable
internal fun MedicationScreenLoadingPreview() {
    MgoTheme {
        MedicationScreenContent(
            providerName = "UMC Groningen",
            viewState = MedicationScreenViewState.Loading,
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun MedicationScreenMedicationsPreview() {
    MgoTheme {
        MedicationScreenContent(
            providerName = "UMC Groningen",
            viewState = MedicationScreenViewState.Success(listOf(TEST_MGO_MEDICATION, TEST_MGO_MEDICATION)),
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun MedicationScreenErrorPreview() {
    MgoTheme {
        MedicationScreenContent(
            providerName = "UMC Groningen",
            viewState = MedicationScreenViewState.Error(isProductionBuild = true, error = IllegalStateException("Something went wrong")),
            onNavigateBack = {},
        )
    }
}

@Composable
private fun MedicationScreenViewState.toResultsScreenViewState(): ResultsScreenViewState {
    return when (this) {
        MedicationScreenViewState.Loading -> ResultsScreenViewState.Loading
        is MedicationScreenViewState.Success -> {
            ResultsScreenViewState.Loaded.Success(cardItems = medications.map { medication -> medication.toCollapsableCardItem() })
        }
        is MedicationScreenViewState.Error -> ResultsScreenViewState.Loaded.Error(error = error, isProductionBuild = isProductionBuild)
    }
}

@Composable
private fun MgoMedication.toCollapsableCardItem(): CollapsableCardItem {
    return CollapsableCardItem(
        title = title ?: "",
        properties =
            listOf(
                CollapsableCardProperty(
                    heading = stringResource(id = CopyR.string.fhir_instructions),
                    value = instructions ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = CopyR.string.fhir_startDate),
                    value = startDate ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = CopyR.string.fhir_prescribedBy),
                    value = prescribedBy ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = CopyR.string.fhir_status),
                    value = status ?: "",
                ),
            ),
    )
}
