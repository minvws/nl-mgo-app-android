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
fun MedicationScreen(onNavigateBack: () -> Unit) {
    val viewModel: MedicationScreenViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()
    MedicationScreenContent(
        viewState = viewState,
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun MedicationScreenContent(
    viewState: MedicationScreenViewState,
    onNavigateBack: () -> Unit,
) {
    ResultsScreen(
        heading = stringResource(id = CopyR.string.medication_title),
        subHeading = stringResource(id = CopyR.string.medication_subtitle, viewState.providerName),
        viewState = viewState.toResultsScreenViewState(),
        onNavigateBack = onNavigateBack,
    )
}

@DefaultPreviews
@Composable
internal fun MedicationScreenLoadingPreview() {
    MgoTheme {
        MedicationScreenContent(
            viewState = MedicationScreenViewState.initialState("UMC Groningen"),
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun MedicationScreenMedicationsPreview() {
    MgoTheme {
        MedicationScreenContent(
            viewState =
                MedicationScreenViewState(
                    providerName = "UMC Groningen",
                    loading = false,
                    medications = listOf(TEST_MGO_MEDICATION, TEST_MGO_MEDICATION),
                    error = null,
                ),
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun MedicationScreenErrorPreview() {
    MgoTheme {
        MedicationScreenContent(
            viewState =
                MedicationScreenViewState(
                    providerName = "UMC Groningen",
                    loading = false,
                    medications = listOf(),
                    error = null,
                ),
            onNavigateBack = {},
        )
    }
}

@Composable
private fun MedicationScreenViewState.toResultsScreenViewState(): ResultsScreenViewState {
    return when {
        loading -> ResultsScreenViewState.Loading
        error != null -> ResultsScreenViewState.Loaded.Error(error = error)
        else -> {
            val cardItems = medications.map { medication -> medication.toCollapsableCardItem() }
            ResultsScreenViewState.Loaded.Success(cardItems = cardItems)
        }
    }
}

@Composable
private fun MgoMedication.toCollapsableCardItem(): CollapsableCardItem {
    return CollapsableCardItem(
        title = title ?: "",
        properties =
            listOf(
                CollapsableCardProperty(
                    heading = stringResource(id = CopyR.string.healthcareprovider_card_instructions_header),
                    value = instructions ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = CopyR.string.healthcareprovider_card_start_date_header),
                    value = startDate ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = CopyR.string.healthcareprovider_card_start_prescribed_by_header),
                    value = prescribedBy ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = CopyR.string.healthcareprovider_card_start_status_header),
                    value = status ?: "",
                ),
            ),
    )
}
