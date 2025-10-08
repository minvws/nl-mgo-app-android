package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup

@HiltViewModel(assistedFactory = HealthCategoriesFavoriteCardViewModel.Factory::class)
internal class HealthCategoriesFavoriteCardViewModel
  @AssistedInject
  constructor(
    @Assisted private val category: HealthCategoryGroup.HealthCategory,
  ) : ViewModel() {
    @AssistedFactory
    interface Factory {
      fun create(category: HealthCategoryGroup.HealthCategory): HealthCategoriesFavoriteCardViewModel
    }

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading = _isLoading.stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {
//      viewModelScope.launch {
//        healthCareDataStatesRepository
//          .observe(category = category, filterOrganization = null)
//          .distinctUntilChanged()
//          .collectLatest { states ->
//            if (states.isNotEmpty()) {
//              val loading = states.any { state -> state is HealthCareDataState.Loading }
//              _isLoading.tryEmit(loading)
//            }
//          }
//      }
    }
  }
