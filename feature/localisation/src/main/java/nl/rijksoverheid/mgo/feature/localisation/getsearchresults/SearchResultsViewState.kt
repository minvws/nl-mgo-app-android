package nl.rijksoverheid.mgo.feature.localisation.getsearchresults

import nl.rijksoverheid.mgo.data.localisation.models.SearchResult

sealed class SearchResultsViewState {
    data object Loading : SearchResultsViewState()

    data class Success(val results: List<SearchResult>) : SearchResultsViewState()

    data class Error(val error: Throwable) : SearchResultsViewState()

    companion object {
        val initialState = Loading
    }
}
