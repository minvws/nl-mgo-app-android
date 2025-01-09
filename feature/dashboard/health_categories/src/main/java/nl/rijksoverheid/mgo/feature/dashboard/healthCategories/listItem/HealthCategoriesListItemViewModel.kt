package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.HealthCareDataStatesRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = HealthCategoriesListItemViewModel.Factory::class)
internal class HealthCategoriesListItemViewModel
    @AssistedInject
    constructor(
        @Assisted private val filterOrganization: MgoOrganization?,
        @Assisted private val category: HealthCareCategory,
        private val healthCareDataStatesRepository: HealthCareDataStatesRepository,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(
                filterOrganization: MgoOrganization?,
                category: HealthCareCategory,
            ): HealthCategoriesListItemViewModel
        }

        private val _listItemState: MutableStateFlow<HealthCategoriesListItemState> =
            MutableStateFlow(
                HealthCategoriesListItemState.LOADING,
            )
        val listItemState = _listItemState.stateIn(viewModelScope, SharingStarted.Lazily, HealthCategoriesListItemState.LOADING)

        init {
            viewModelScope.launch(Dispatchers.IO) {
                healthCareDataStatesRepository.observe(category = category, filterOrganization = filterOrganization).distinctUntilChanged()
                    .collectLatest { states ->
                        if (states.isNotEmpty()) {
                            val loading = states.any { state -> state is HealthCareDataState.Loading }
                            val empty = states.all { state -> state is HealthCareDataState.Empty }
                            val amountOfItems =
                                states
                                    .filterIsInstance<HealthCareDataState.Loaded>()
                                    .sumOf { state -> state.results.sumOf { it.getOrNull()?.size ?: 0 } }
                            when {
                                loading -> _listItemState.update { HealthCategoriesListItemState.LOADING }
                                empty -> _listItemState.update { HealthCategoriesListItemState.NO_DATA }
                                amountOfItems == 0 -> _listItemState.update { HealthCategoriesListItemState.NO_DATA }
                                else -> _listItemState.update { HealthCategoriesListItemState.LOADED }
                            }
                        }
                    }
            }
        }
    }
