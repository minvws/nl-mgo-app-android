package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.HealthCareData
import nl.rijksoverheid.mgo.data.healthcare.HealthCareRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = HealthCategoriesListItemViewModel.Factory::class)
internal class HealthCategoriesListItemViewModel
    @AssistedInject
    constructor(
        @Assisted private val filterOrganization: MgoOrganization?,
        @Assisted private val category: HealthCareCategory,
        private val healthCareRepository: HealthCareRepository,
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
            viewModelScope.launch {
                healthCareRepository.observeData(category = category, filterOrganization = filterOrganization)
                    .collectLatest { healthCareDataList ->
                        val loading = healthCareDataList.any { it is HealthCareData.Loading }
                        val amountOfItems =
                            healthCareDataList
                                .filterIsInstance<HealthCareData.Loaded>()
                                .map { it.uiSchemaList }
                                .flatten()
                                .count()
                        val error = healthCareDataList.all { it is HealthCareData.Error }
                        when {
                            loading -> _listItemState.update { HealthCategoriesListItemState.LOADING }
                            error -> _listItemState.update { HealthCategoriesListItemState.NO_DATA }
                            amountOfItems > 0 -> _listItemState.update { HealthCategoriesListItemState.LOADED }
                            else -> _listItemState.update { HealthCategoriesListItemState.NO_DATA }
                        }
                    }
            }
        }
    }
