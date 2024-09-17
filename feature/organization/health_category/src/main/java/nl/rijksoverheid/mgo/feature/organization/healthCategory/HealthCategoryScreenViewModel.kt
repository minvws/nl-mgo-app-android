package nl.rijksoverheid.mgo.feature.organization.healthCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.HealthCareData
import nl.rijksoverheid.mgo.data.healthcare.HealthCareRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = HealthCategoryScreenViewModel.Factory::class)
class HealthCategoryScreenViewModel
    @AssistedInject
    constructor(
        @Assisted("arguments") private val arguments: HealthCategoryScreenArguments,
        private val healthCareRepository: HealthCareRepository,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(
                @Assisted("arguments") arguments: HealthCategoryScreenArguments,
            ): HealthCategoryScreenViewModel
        }

        private val initialState = HealthCategoryScreenViewState.initialState
        private val _viewState: MutableStateFlow<HealthCategoryScreenViewState> = MutableStateFlow(initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

        init {
            viewModelScope.launch {
                healthCareRepository.observeData(
                    category = HealthCareCategory.MEDICATIONS,
                    filterOrganization = arguments.filterOrganization,
                )
                    .collectLatest { healthCareDataList ->
                        val listItems =
                            healthCareDataList.filterIsInstance<HealthCareData.Loaded>().map { healthCareData ->
                                healthCareData.uiSchemaList.map { uiSchema ->
                                    HealthCategoryScreenListItem(
                                        title = uiSchema.label ?: "",
                                        subtitle = healthCareData.organization.name,
                                        uiSchema = uiSchema,
                                    )
                                }
                            }.flatten()
                        _viewState.update { viewState -> viewState.copy(listItems = listItems) }
                    }
            }
        }
    }
