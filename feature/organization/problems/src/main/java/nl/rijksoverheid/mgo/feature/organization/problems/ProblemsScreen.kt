package nl.rijksoverheid.mgo.feature.organization.problems

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
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@Composable
fun ProblemsScreen(
    provider: MgoOrganization,
    onNavigateBack: () -> Unit,
) {
    val viewModel =
        hiltViewModel<ProblemsScreenViewModel, ProblemsScreenViewModel.Factory>(
            creationCallback = { factory -> factory.create(provider) },
        )
    val viewState by viewModel.viewState.collectAsState()
    ProblemsScreenContent(
        viewState = viewState,
        onNavigateBack = onNavigateBack,
    )
}

@Composable
private fun ProblemsScreenContent(
    viewState: ProblemsScreenViewState,
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
internal fun ProblemsScreenLoadingPreview() {
    MgoTheme {
        ProblemsScreenContent(
            viewState = ProblemsScreenViewState.initialState,
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun ProblemsScreenConcernsPreview() {
    MgoTheme {
        ProblemsScreenContent(
            viewState = ProblemsScreenViewState.initialState.copy(loading = false, concerns = listOf(TEST_MGO_CONCERN, TEST_MGO_CONCERN)),
            onNavigateBack = {},
        )
    }
}

@DefaultPreviews
@Composable
internal fun ProblemsScreenErrorPreview() {
    MgoTheme {
        ProblemsScreenContent(
            viewState = ProblemsScreenViewState.initialState.copy(loading = false, error = IllegalStateException("Something went wrong")),
            onNavigateBack = {},
        )
    }
}

@Composable
private fun ProblemsScreenViewState.toResultsScreenViewState(): ResultsScreenViewState {
    return when {
        loading -> ResultsScreenViewState.Loading
        error != null -> ResultsScreenViewState.Loaded.Error(error = error)
        else -> {
            val cardItems = concerns.map { concern -> concern.toCollapsableCardItem() }
            ResultsScreenViewState.Loaded.Success(cardItems = cardItems)
        }
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
