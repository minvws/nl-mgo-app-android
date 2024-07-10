package nl.rijksoverheid.mgo.component.results

import nl.rijksoverheid.mgo.component.collapsablecard.CollapsableCardItem

sealed class ResultsScreenViewState {
    data object Loading : ResultsScreenViewState()

    sealed class Loaded : ResultsScreenViewState() {
        data class Success(val cardItems: List<CollapsableCardItem>) : Loaded()

        data class Error(val error: Throwable) : Loaded()
    }
}
