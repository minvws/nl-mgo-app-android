package nl.rijksoverheid.mgo.feature.organization.healthCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.HealthCareData
import nl.rijksoverheid.mgo.data.healthcare.HealthCareRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HealthCategoryScreenViewModel
    @Inject
    constructor(private val healthCareRepository: HealthCareRepository) : ViewModel() {
        private val initialState = HealthCategoryScreenViewState.initialState
        private val _viewState: MutableStateFlow<HealthCategoryScreenViewState> = MutableStateFlow(initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

        init {
            viewModelScope.launch {
                healthCareRepository.observeData(HealthCareCategory.MEDICATIONS).collectLatest { healthCareDataList ->
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
