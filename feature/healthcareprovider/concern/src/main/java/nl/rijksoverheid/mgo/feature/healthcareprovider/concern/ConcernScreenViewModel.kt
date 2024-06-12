package nl.rijksoverheid.mgo.feature.healthcareprovider.concern

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.concern.ConcernRepository
import nl.rijksoverheid.mgo.framework.environment.AppInfo
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ConcernScreenViewModel
    @Inject
    constructor(private val appInfo: AppInfo, private val concernRepository: ConcernRepository) : ViewModel() {
        private val _viewState: MutableStateFlow<ConcernScreenViewState> = MutableStateFlow(ConcernScreenViewState.Loading)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, ConcernScreenViewState.Loading)

        init {
            viewModelScope.launch {
                _viewState.update { ConcernScreenViewState.Loading }
                concernRepository
                    .getConcerns()
                    .onSuccess { concerns ->
                        _viewState.update { ConcernScreenViewState.Success(concerns) }
                    }
                    .onFailure { throwable ->
                        _viewState.update {
                            ConcernScreenViewState.Error(
                                isProductionBuild = appInfo.isProductionBuild(),
                                error = throwable,
                            )
                        }
                    }
            }
        }
    }
