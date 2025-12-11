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
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import javax.inject.Named

@HiltViewModel(assistedFactory = HealthCategoriesFavoriteCardViewModel.Factory::class)
internal class HealthCategoriesFavoriteCardViewModel
  @AssistedInject
  constructor(
    @Assisted private val category: HealthCategoryGroup.HealthCategory,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
    private val organizationRepository: OrganizationRepository,
    private val fhirRepository: FhirRepository,
    private val getEndpointsForHealthCategory: GetEndpointsForHealthCategory,
  ) : ViewModel() {
    @AssistedFactory
    interface Factory {
      fun create(category: HealthCategoryGroup.HealthCategory): HealthCategoriesFavoriteCardViewModel
    }

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading = _isLoading.stateIn(viewModelScope, SharingStarted.Lazily, false)

    init {
      viewModelScope.launch(ioDispatcher) {
        organizationRepository.storedOrganizationsFlow.collectLatest { organizations ->
          // Always start with loading state whenever a organization has been added
          _isLoading.update { true }

          // Get all the fhir responses for this category that we can observe
          val fhirResponseFlows =
            organizations
              .map { organization ->
                val endpoints = getEndpointsForHealthCategory(category = category, organization = organization)
                endpoints.map { endpoint ->
                  fhirRepository.observe(
                    organizationId = organization.id,
                    dataServiceId = endpoint.dataServiceId,
                    endpointId = endpoint.endpointId,
                  )
                }
              }.flatten()

          if (fhirResponseFlows.isEmpty()) {
            _isLoading.update { false }
          } else {
            // Observe the fhir responses
            combine(fhirResponseFlows) { responses -> responses.toList() }.collectLatest {
              _isLoading.update { false }
            }
          }
        }
      }
    }
  }
