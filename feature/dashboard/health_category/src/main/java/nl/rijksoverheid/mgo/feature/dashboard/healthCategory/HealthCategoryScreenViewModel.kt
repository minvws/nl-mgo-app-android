package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.healthcare.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.HealthCareStateRepository
import nl.rijksoverheid.mgo.data.healthcare.getTitle
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
        private val healthCareStateRepository: HealthCareStateRepository,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(
                @Assisted("arguments") arguments: HealthCategoryScreenArguments,
            ): HealthCategoryScreenViewModel
        }

        private val initialState = HealthCategoryScreenViewState.initialState(arguments.category)
        private val _viewState: MutableStateFlow<HealthCategoryScreenViewState> = MutableStateFlow(initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

        init {
            viewModelScope.launch {
                healthCareStateRepository.observe(
                    category = arguments.category,
                    organization = arguments.filterOrganization,
                )
                    .collectLatest { states ->
                        val loading = states.any { state -> state.loading }
                        val listItems = states.map { state -> state.toListItems() }.flatten()
                        _viewState.update {
                            val listItemState =
                                when {
                                    loading -> HealthCategoryScreenViewState.ListItemsState.Loading
                                    listItems.isEmpty() -> HealthCategoryScreenViewState.ListItemsState.NoData
                                    else -> HealthCategoryScreenViewState.ListItemsState.Loaded(listItems)
                                }
                            HealthCategoryScreenViewState(title = arguments.category.getTitle(), listItemsState = listItemState)
                        }
                    }
            }
        }

        private fun HealthCareDataState.toListItems(): List<HealthCategoryScreenListItem> {
            return uiSchemaListResults
                .map { it.getOrNull() ?: listOf() }
                .flatten()
                .map { uiSchema ->
                    HealthCategoryScreenListItem(
                        title = uiSchema.label ?: "",
                        subtitle = organization.name,
                        uiSchema = uiSchema,
                    )
                }
        }
    }
