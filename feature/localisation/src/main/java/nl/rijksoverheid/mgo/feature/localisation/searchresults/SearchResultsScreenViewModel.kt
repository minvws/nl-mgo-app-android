package nl.rijksoverheid.mgo.feature.localisation.searchresults

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.SearchRepository
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
internal class SearchResultsScreenViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val searchRepository: SearchRepository,
    ) : ViewModel() {
        private val name = NavigationScreen.Localisation.SearchResults.getName(savedStateHandle)
        private val city = NavigationScreen.Localisation.SearchResults.getCity(savedStateHandle)

        private val _viewState = MutableStateFlow<SearchResultsViewState>(SearchResultsViewState.initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, SearchResultsViewState.initialState)

        init {
            getSearchResults()
        }

        private fun getSearchResults() {
            viewModelScope.launch {
                searchRepository.search(name = name, city = city)
                    .onSuccess { results -> _viewState.update { SearchResultsViewState.Success(name, city, results) } }
                    .onFailure { throwable -> _viewState.update { SearchResultsViewState.Error(throwable) } }
            }
        }
    }
