package nl.rijksoverheid.mgo.feature.healthcareprovider.medication

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.medication.MedicationRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MedicationScreenViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val medicationRepository: MedicationRepository,
    ) : ViewModel() {
        private val providerName: String = requireNotNull(savedStateHandle[MEDICATION_SCREEN_VIEW_MODEL_PROVIDER_NAME])
        private val initialState = MedicationScreenViewState.initialState(providerName = providerName)
        private val _viewState: MutableStateFlow<MedicationScreenViewState> = MutableStateFlow(initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

        init {
            viewModelScope.launch {
                medicationRepository
                    .getMedications()
                    .onSuccess { medications ->
                        _viewState.update { viewState -> viewState.copy(loading = false, medications = medications) }
                    }
                    .onFailure { error ->
                        _viewState.update { viewState -> viewState.copy(loading = false, error = error) }
                    }
            }
        }
    }
