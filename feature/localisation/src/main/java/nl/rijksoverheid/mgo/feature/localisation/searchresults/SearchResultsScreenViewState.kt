package nl.rijksoverheid.mgo.feature.localisation.searchresults

import nl.rijksoverheid.mgo.data.localisation.models.SearchResult

sealed class SearchResultsScreenViewState {
    data object Loading : SearchResultsScreenViewState()

    data class Success(val name: String, val city: String, val results: List<SearchResult>) : SearchResultsScreenViewState()

    data class Error(val isProductionBuild: Boolean, val error: Throwable) : SearchResultsScreenViewState()

    companion object {
        val initialState = Loading
    }
}
