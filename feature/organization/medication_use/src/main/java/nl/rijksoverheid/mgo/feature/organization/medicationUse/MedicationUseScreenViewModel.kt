package nl.rijksoverheid.mgo.feature.organization.medicationUse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.medication.MedicationRepository
import timber.log.Timber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MedicationUseScreenViewModel.Factory::class)
class MedicationUseScreenViewModel
    @AssistedInject
    constructor(
        @Assisted val provider: MgoOrganization,
        private val medicationRepository: MedicationRepository,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(provider: MgoOrganization): MedicationUseScreenViewModel
        }

        private val initialState = MedicationUseScreenViewState.initialState
        private val _viewState: MutableStateFlow<MedicationUseScreenViewState> = MutableStateFlow(initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

        init {
            viewModelScope.launch {
                medicationRepository
                    .getMedications(provider.id, provider.resourceEndpoint)
                    .onSuccess { uiSchemaList ->
                        _viewState.update { viewState -> viewState.copy(uiSchemaList = uiSchemaList) }
                    }
                    .onFailure { error ->
                        // This flow will soon no longer exist.
                        Timber.e(error, "Something went wrong")
                    }
            }
        }
    }
