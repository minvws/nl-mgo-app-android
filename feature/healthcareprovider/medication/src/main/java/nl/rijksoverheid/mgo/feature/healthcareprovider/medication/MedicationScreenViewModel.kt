package nl.rijksoverheid.mgo.feature.healthcareprovider.medication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.medication.MedicationRepository
import nl.rijksoverheid.mgo.framework.environment.AppFlavor
import nl.rijksoverheid.mgo.framework.environment.AppInfo
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MedicationScreenViewModel
    @Inject
    constructor(private val appInfo: AppInfo, private val medicationRepository: MedicationRepository) : ViewModel() {
        private val _viewState: MutableStateFlow<MedicationScreenViewState> = MutableStateFlow(MedicationScreenViewState.Loading)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, MedicationScreenViewState.Loading)

        init {
            viewModelScope.launch {
                _viewState.update { MedicationScreenViewState.Loading }
                medicationRepository
                    .getMedications()
                    .onSuccess { medications ->
                        _viewState.update { MedicationScreenViewState.Success(medications) }
                    }
                    .onFailure { throwable ->
                        _viewState.update {
                            MedicationScreenViewState.Error(
                                isProductionBuild = appInfo.appFlavor == AppFlavor.PROD,
                                error = throwable,
                            )
                        }
                    }
            }
        }
    }
