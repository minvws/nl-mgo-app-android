package nl.rijksoverheid.mgo.feature.localisation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.framework.copy.R as FrameworkR

@HiltViewModel
class SearchScreenViewModel
    @Inject
    constructor() : ViewModel() {
        private val _viewState = MutableStateFlow(SearchScreenViewState.initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, SearchScreenViewState.initialState)

        private val _navigation = MutableSharedFlow<NavigationScreen>(extraBufferCapacity = 1)
        val navigation = _navigation.asSharedFlow()

        fun setName(name: String) {
            _viewState.update { viewState -> viewState.copy(name = name) }
        }

        fun setCity(city: String) {
            _viewState.update { viewState -> viewState.copy(city = city) }
        }

        fun getSearchResults() {
            viewModelScope.launch {
                val name = _viewState.value.name
                val city = _viewState.value.city
                val nameError = if (name.isEmpty()) FrameworkR.string.localisation_search_name_error else null
                val cityError = if (city.isEmpty()) FrameworkR.string.localisation_search_city_error else null
                _viewState.update { viewState -> viewState.copy(nameError = nameError, cityError = cityError) }
                if (nameError == null && cityError == null) {
                    _navigation.tryEmit(NavigationScreen.AddHealthCare.GetSearchResults)
                }
            }
        }
    }
