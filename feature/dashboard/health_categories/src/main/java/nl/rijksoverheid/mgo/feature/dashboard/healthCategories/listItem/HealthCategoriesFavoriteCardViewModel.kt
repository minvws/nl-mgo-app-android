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
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.HealthCareDataStatesRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId

@HiltViewModel(assistedFactory = HealthCategoriesFavoriteCardViewModel.Factory::class)
internal class HealthCategoriesFavoriteCardViewModel
  @AssistedInject
  constructor(
    @Assisted private val category: HealthCareCategoryId,
    private val healthCareDataStatesRepository: HealthCareDataStatesRepository,
  ) : ViewModel() {
    @AssistedFactory
    interface Factory {
      fun create(category: HealthCareCategoryId): HealthCategoriesFavoriteCardViewModel
    }

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading = _isLoading.stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {
      viewModelScope.launch {
        healthCareDataStatesRepository
          .observe(category = category, filterOrganization = null)
          .distinctUntilChanged()
          .collectLatest { states ->
            if (states.isNotEmpty()) {
              val loading = states.any { state -> state is HealthCareDataState.Loading }
              _isLoading.tryEmit(loading)
            }
          }
      }
    }
  }
