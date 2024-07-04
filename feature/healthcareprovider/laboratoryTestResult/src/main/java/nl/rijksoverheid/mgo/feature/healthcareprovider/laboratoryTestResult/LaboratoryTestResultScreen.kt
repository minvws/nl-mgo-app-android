package nl.rijksoverheid.mgo.feature.healthcareprovider.laboratoryTestResult

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
import nl.rijksoverheid.mgo.data.laboratoryTestResult.models.MgoLaboratoryTestResult
import nl.rijksoverheid.mgo.data.laboratoryTestResult.models.TEST_MGO_LABORATORY_TEST_RESULT
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun LaboratoryTestResultScreen(onNavigateBack: () -> Unit) {
    val viewModel: LaboratoryTestResultScreenViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()
    LaboratoryTestResultScreenContent(
        viewState = viewState,
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun LaboratoryTestResultScreenContent(
    viewState: LaboratoryTestResultScreenViewState,
    onNavigateBack: () -> Unit,
) {
    ResultsScreen(
        heading = stringResource(id = CopyR.string.lab_results_heading),
        subHeading = stringResource(id = CopyR.string.lab_results_subheading),
        viewState = viewState.toResultsScreenViewState(),
        onNavigateBack = onNavigateBack,
    )
}

@DefaultPreviews
@Composable
internal fun LaboratoryTestResultScreenLoadingPreview() {
    MgoTheme {
        LaboratoryTestResultScreenContent(
            viewState = LaboratoryTestResultScreenViewState.Loading,
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun LaboratoryTestResultScreenTestResultsPreview() {
    MgoTheme {
        LaboratoryTestResultScreenContent(
            viewState =
                LaboratoryTestResultScreenViewState.Success(
                    listOf(
                        TEST_MGO_LABORATORY_TEST_RESULT,
                        TEST_MGO_LABORATORY_TEST_RESULT,
                    ),
                ),
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun LaboratoryTestResultScreenErrorPreview() {
    MgoTheme {
        LaboratoryTestResultScreenContent(
            viewState =
                LaboratoryTestResultScreenViewState.Error(
                    isProductionBuild = true,
                    error = IllegalStateException("Something went wrong"),
                ),
            onNavigateBack = {},
        )
    }
}

@Composable
private fun LaboratoryTestResultScreenViewState.toResultsScreenViewState(): ResultsScreenViewState {
    return when (this) {
        LaboratoryTestResultScreenViewState.Loading -> ResultsScreenViewState.Loading
        is LaboratoryTestResultScreenViewState.Success -> {
            ResultsScreenViewState.Loaded.Success(cardItems = testResults.map { testResult -> testResult.toCollapsableCardItem() })
        }
        is LaboratoryTestResultScreenViewState.Error ->
            ResultsScreenViewState.Loaded.Error(
                error = error,
                isProductionBuild = isProductionBuild,
            )
    }
}

@Composable
private fun MgoLaboratoryTestResult.toCollapsableCardItem(): CollapsableCardItem {
    return CollapsableCardItem(
        title = title ?: "",
        properties =
            listOf(
                CollapsableCardProperty(
                    heading = stringResource(id = R.string.fhir_code),
                    value = code ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = R.string.fhir_status),
                    value = status ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = R.string.fhir_dateTime),
                    value = dateTime ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = R.string.fhir_result),
                    value = result ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = R.string.fhir_referenceRangeLow),
                    value = referenceRangeLow ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = R.string.fhir_referenceRangeHigh),
                    value = referenceRangeHigh ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = R.string.fhir_interpretation),
                    value = interpretation ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = R.string.fhir_specimen),
                    value = specimen ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = R.string.fhir_collectionDateTime),
                    value = collectionDateTime ?: "",
                ),
            ),
    )
}
