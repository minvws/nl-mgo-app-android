package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.HealthCareDataStatesRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

/**
 * The [ViewModel] for [HealthCategoriesListItem].
 * Observes health care data for the [HealthCareCategoryId].
 *
 * @param filterOrganization If not null, will observe health care data for this organization. If null will observe for all added
 * organizations.
 * @param category The [HealthCareCategoryId] to determine which health care data falls into this category.
 * @param healthCareDataStatesRepository The [HealthCareDataStatesRepository] that is responsible for fetching the health care data.
 */
@HiltViewModel(assistedFactory = HealthCategoriesListItemViewModel.Factory::class)
internal class HealthCategoriesListItemViewModel
  @AssistedInject
  constructor(
    @Assisted private val filterOrganization: MgoOrganization?,
    @Assisted private val category: HealthCareCategoryId,
    private val healthCareDataStatesRepository: HealthCareDataStatesRepository,
  ) : ViewModel() {
    @AssistedFactory
    interface Factory {
      fun create(
        filterOrganization: MgoOrganization?,
        category: HealthCareCategoryId,
      ): HealthCategoriesListItemViewModel
    }

    private val _listItemState: MutableStateFlow<HealthCategoriesListItemState> =
      MutableStateFlow(
        HealthCategoriesListItemState.LOADING,
      )
    val listItemState = _listItemState.stateIn(viewModelScope, SharingStarted.Lazily, HealthCategoriesListItemState.LOADING)

    init {
      viewModelScope.launch {
        healthCareDataStatesRepository
          .observe(category = category, filterOrganization = filterOrganization)
          .distinctUntilChanged()
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
