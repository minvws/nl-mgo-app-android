package nl.rijksoverheid.mgo.feature.organization.labResults

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.laboratoryTestResult.LaboratoryTestResultRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = LabResultsScreenViewModel.Factory::class)
class LabResultsScreenViewModel
    @AssistedInject
    constructor(
        @Assisted val provider: MgoOrganization,
        private val laboratoryTestResultRepository: LaboratoryTestResultRepository,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(provider: MgoOrganization): LabResultsScreenViewModel
        }

        private val initialState = LabResultsScreenViewState.initialState
        private val _viewState: MutableStateFlow<LabResultsScreenViewState> = MutableStateFlow(initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

        init {
            viewModelScope.launch {
                _viewState.update { viewState -> viewState.copy(loading = true) }
                laboratoryTestResultRepository
                    .getLaboratoryTestResults(provider.resourceEndpoint)
                    .onSuccess { testResults ->
                        _viewState.update { viewState -> viewState.copy(loading = false, testResults = testResults) }
                    }
                    .onFailure { error ->
                        _viewState.update { viewState -> viewState.copy(loading = false, error = error) }
                    }
            }
        }
    }
