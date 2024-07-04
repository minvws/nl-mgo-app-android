package nl.rijksoverheid.mgo.feature.healthcareprovider.concern

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
import nl.rijksoverheid.mgo.data.concern.models.MgoConcern
import nl.rijksoverheid.mgo.data.concern.models.TEST_MGO_CONCERN
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun ConcernScreen(onNavigateBack: () -> Unit) {
    val viewModel: ConcernScreenViewModel = hiltViewModel()
    val viewState by viewModel.viewState.collectAsState()
    ConcernScreenContent(
        viewState = viewState,
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun ConcernScreenContent(
    viewState: ConcernScreenViewState,
    onNavigateBack: () -> Unit,
) {
    ResultsScreen(
        heading = stringResource(id = CopyR.string.problems_heading),
        subHeading = stringResource(id = CopyR.string.problems_subheading),
        viewState = viewState.toResultsScreenViewState(),
        onNavigateBack = onNavigateBack,
    )
}

@DefaultPreviews
@Composable
internal fun ConcernScreenLoadingPreview() {
    MgoTheme {
        ConcernScreenContent(
            viewState = ConcernScreenViewState.Loading,
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun ConcernScreenConcernsPreview() {
    MgoTheme {
        ConcernScreenContent(
            viewState = ConcernScreenViewState.Success(listOf(TEST_MGO_CONCERN, TEST_MGO_CONCERN)),
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun ConcernScreenErrorPreview() {
    MgoTheme {
        ConcernScreenContent(
            viewState = ConcernScreenViewState.Error(isProductionBuild = true, error = IllegalStateException("Something went wrong")),
            onNavigateBack = {},
        )
    }
}

@Composable
private fun ConcernScreenViewState.toResultsScreenViewState(): ResultsScreenViewState {
    return when (this) {
        ConcernScreenViewState.Loading -> ResultsScreenViewState.Loading
        is ConcernScreenViewState.Success -> {
            ResultsScreenViewState.Loaded.Success(cardItems = concerns.map { concern -> concern.toCollapsableCardItem() })
        }

        is ConcernScreenViewState.Error -> ResultsScreenViewState.Loaded.Error(error = error)
    }
}

@Composable
private fun MgoConcern.toCollapsableCardItem(): CollapsableCardItem {
    return CollapsableCardItem(
        title = title ?: "",
        properties =
            listOf(
                CollapsableCardProperty(
                    heading = stringResource(id = R.string.fhir_category),
                    value = category ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = R.string.fhir_clinicalStatus),
                    value = clinicalStatus ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = R.string.fhir_startDate),
                    value = startDate ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = R.string.fhir_endDate),
                    value = endDate ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = R.string.fhir_bodyLocation),
                    value = bodyLocation ?: "",
                ),
                CollapsableCardProperty(
                    heading = stringResource(id = R.string.fhir_comment),
                    value = comment ?: "",
                ),
            ),
    )
}
