package nl.rijksoverheid.mgo.feature.localisation.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.HealthCareProviderRepository
import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AddHealthCareProviderScreenViewModel
    @Inject
    constructor(
        private val healthCareProviderRepository: HealthCareProviderRepository,
    ) : ViewModel() {
        private val _viewState = MutableStateFlow(AddHealthCareProviderViewState.initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, AddHealthCareProviderViewState.initialState)

        init {
            get()
        }

        private fun get() {
            viewModelScope.launch {
                val providers = healthCareProviderRepository.get()
                _viewState.update { viewState -> viewState.copy(providers = providers) }
            }
        }

        fun add(provider: HealthCareProvider) {
            viewModelScope.launch {
                _viewState.update { viewState ->
                    val newProviders = viewState.providers.toMutableList()
                    newProviders.add(provider)
                    viewState.copy(providers = newProviders)
                }
            }
        }

        fun save() {
            viewModelScope.launch {
                // Get all providers we want to add
                val providers = viewState.value.providers

                // Save them on disk
                for (provider in providers) {
                    healthCareProviderRepository.save(provider)
                }
            }
        }

        fun delete(provider: HealthCareProvider) {
            viewModelScope.launch {
                _viewState.update { viewState ->
                    val newProviders = viewState.providers.toMutableList()
                    newProviders.remove(provider)
                    viewState.copy(providers = newProviders)
                }
            }
        }
    }
