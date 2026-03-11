package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.fhir.GetRequests
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import javax.inject.Named

@HiltViewModel(assistedFactory = HealthCategoriesFavoriteCardViewModel.Factory::class)
internal class HealthCategoriesFavoriteCardViewModel
  @AssistedInject
  constructor(
    @Assisted private val category: HealthCategoryGroup.HealthCategory,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val organizationRepository: OrganizationRepository,
    private val fhirRepository: FhirRepository,
    private val getRequests: GetRequests,
  ) : ViewModel() {
    @AssistedFactory
    interface Factory {
      fun create(category: HealthCategoryGroup.HealthCategory): HealthCategoriesFavoriteCardViewModel
    }

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading = _isLoading.stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {
      viewModelScope.launch(ioDispatcher) {
        organizationRepository.getSaved(coroutineContext).collectLatest { organizations ->
          // Always start with loading state whenever a organization has been added
          _isLoading.update { true }

          // Get requests
          val requests = getRequests(categories = listOf(category), organizations = organizations)

          // Get response flows to observe
          val responseFlows = requests.map { request -> fhirRepository.observe(request) }

          if (responseFlows.isEmpty()) {
            _isLoading.update { false }
          } else {
            // Observe the responses
            combine(responseFlows) { responses -> responses.toList() }.collectLatest {
              _isLoading.update { false }
            }
          }
        }
      }
    }
  }
