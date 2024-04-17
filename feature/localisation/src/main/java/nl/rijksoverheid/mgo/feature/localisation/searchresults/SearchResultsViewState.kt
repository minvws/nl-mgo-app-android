package nl.rijksoverheid.mgo.feature.localisation.searchresults

import nl.rijksoverheid.mgo.data.localisation.models.SearchResult

sealed class SearchResultsViewState {
    data object Loading : SearchResultsViewState()

    data class Success(val name: String, val city: String, val results: List<SearchResult>) : SearchResultsViewState()

    data class Error(val error: Throwable) : SearchResultsViewState()

    companion object {
        val initialState = Loading
    }
}
