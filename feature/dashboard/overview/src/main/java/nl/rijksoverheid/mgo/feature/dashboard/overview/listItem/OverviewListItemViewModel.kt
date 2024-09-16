package nl.rijksoverheid.mgo.feature.dashboard.overview.listItem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.HealthCareData
import nl.rijksoverheid.mgo.data.healthcare.HealthCareRepository
import nl.rijksoverheid.mgo.feature.dashboard.overview.HealthCategoriesScreenType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = OverviewListItemViewModel.Factory::class)
internal class OverviewListItemViewModel
    @AssistedInject
    constructor(
        @Assisted private val screenType: HealthCategoriesScreenType,
        @Assisted private val category: HealthCareCategory,
        private val healthCareRepository: HealthCareRepository,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(
                screenType: HealthCategoriesScreenType,
                category: HealthCareCategory,
            ): OverviewListItemViewModel
        }

        private val _listItemState: MutableStateFlow<OverviewListItemState> = MutableStateFlow(OverviewListItemState.LOADING)
        val listItemState = _listItemState.stateIn(viewModelScope, SharingStarted.Lazily, OverviewListItemState.LOADING)

        init {
            viewModelScope.launch {
                val filterOrganization =
                    when (screenType) {
                        is HealthCategoriesScreenType.All -> null
                        is HealthCategoriesScreenType.Single -> screenType.organization
                    }
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
                            loading -> _listItemState.update { OverviewListItemState.LOADING }
                            error -> _listItemState.update { OverviewListItemState.NO_DATA }
                            amountOfItems > 0 -> _listItemState.update { OverviewListItemState.LOADED }
                            else -> _listItemState.update { OverviewListItemState.NO_DATA }
                        }
                    }
            }
        }
    }
