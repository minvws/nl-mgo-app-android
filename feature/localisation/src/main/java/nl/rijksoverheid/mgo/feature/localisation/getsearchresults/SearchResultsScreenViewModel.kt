package nl.rijksoverheid.mgo.feature.localisation.getsearchresults

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import timber.log.Timber
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
internal class SearchResultsScreenViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val name = NavigationScreen.Localisation.SearchResults.getName(savedStateHandle)
        private val city = NavigationScreen.Localisation.SearchResults.getCity(savedStateHandle)

        private val _viewState = MutableStateFlow(SearchResultsViewState.initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, SearchResultsViewState.initialState)

        fun getSearchResults() {
            viewModelScope.launch {
                Timber.v("Name: " + name)
                Timber.v("City: " + city)
            }
        }
    }
