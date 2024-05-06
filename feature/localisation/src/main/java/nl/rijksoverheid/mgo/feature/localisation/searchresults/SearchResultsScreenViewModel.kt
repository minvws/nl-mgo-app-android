package nl.rijksoverheid.mgo.feature.localisation.searchresults

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.HealthCareProviderRepository
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import nl.rijksoverheid.mgo.framework.environment.AppFlavor
import nl.rijksoverheid.mgo.framework.environment.AppInfo
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SearchResultsScreenViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val appInfo: AppInfo,
        private val healthCareProviderRepository: HealthCareProviderRepository,
    ) : ViewModel() {
        private val name = NavigationScreen.Localisation.SearchResults.getName(savedStateHandle)
        private val city = NavigationScreen.Localisation.SearchResults.getCity(savedStateHandle)

        private val _navigation = MutableSharedFlow<NavigationScreen>(extraBufferCapacity = 1)
        val navigation = _navigation.asSharedFlow()

        private val _viewState: MutableStateFlow<SearchResultsScreenViewState> = MutableStateFlow(SearchResultsScreenViewState.initialState)
        val viewState =
            _viewState.stateIn(
                viewModelScope,
                SharingStarted.Lazily,
                SearchResultsScreenViewState
                    .initialState,
            )

        init {
            getSearchResults()
        }

        fun getSearchResults() {
            viewModelScope.launch {
                _viewState.update { SearchResultsScreenViewState.Loading }
                healthCareProviderRepository
                    .search(name = name, city = city)
                    .catch { throwable ->
                        _viewState.update {
                            SearchResultsScreenViewState.Error(
                                isProductionBuild = appInfo.appFlavor == AppFlavor.PROD,
                                error = throwable,
                            )
                        }
                    }
                    .collectLatest { results ->
                        _viewState.update { SearchResultsScreenViewState.Success(name = name, city = city, results = results) }
                    }
            }
        }

        fun addHealthCareProvider(provider: HealthCareProvider) {
            viewModelScope.launch {
                healthCareProviderRepository.save(provider)
                _navigation.tryEmit(NavigationScreen.Localisation.Overview)
            }
        }
    }
