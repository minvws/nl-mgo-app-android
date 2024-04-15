package nl.rijksoverheid.mgo.feature.localisation.getsearchresults

import nl.rijksoverheid.mgo.data.localisation.models.SearchResult

internal class SearchResultsViewState(
    val loading: Boolean,
    val results: List<SearchResult>,
    val error: Throwable? = null,
) {
    companion object {
        val initialState =
            SearchResultsViewState(
                loading = true,
                results = listOf(),
                error = null,
            )
    }
}
