package nl.rijksoverheid.mgo.feature.healthcareprovider.laboratoryTestResult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.laboratoryTestResult.LaboratoryTestResultRepository
import nl.rijksoverheid.mgo.framework.environment.AppInfo
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class LaboratoryTestResultScreenViewModel
    @Inject
    constructor(private val appInfo: AppInfo, private val laboratoryTestResultRepository: LaboratoryTestResultRepository) : ViewModel() {
        private val _viewState: MutableStateFlow<LaboratoryTestResultScreenViewState> =
            MutableStateFlow(LaboratoryTestResultScreenViewState.Loading)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, LaboratoryTestResultScreenViewState.Loading)

        init {
            viewModelScope.launch {
                _viewState.update { LaboratoryTestResultScreenViewState.Loading }
                laboratoryTestResultRepository
                    .getLaboratoryTestResults()
                    .onSuccess { testResults ->
                        _viewState.update { LaboratoryTestResultScreenViewState.Success(testResults) }
                    }
                    .onFailure { throwable ->
                        _viewState.update {
                            LaboratoryTestResultScreenViewState.Error(
                                isProductionBuild = appInfo.isProductionBuild(),
                                error = throwable,
                            )
                        }
                    }
            }
        }
    }
