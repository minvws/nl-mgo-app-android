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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import javax.inject.Named

@HiltViewModel(assistedFactory = HealthCategoriesListItemViewModel.Factory::class)
internal class HealthCategoriesListItemViewModel
  @AssistedInject
  constructor(
    @Assisted private val filterOrganization: MgoOrganization?,
    @Assisted private val category: HealthCategoryGroup.HealthCategory,
    private val getEndpointsForHealthCategory: GetEndpointsForHealthCategory,
    private val organizationRepository: OrganizationRepository,
    private val fhirRepository: FhirRepository,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
  ) : ViewModel() {
    @AssistedFactory
    interface Factory {
      fun create(
        filterOrganization: MgoOrganization?,
        category: HealthCategoryGroup.HealthCategory,
      ): HealthCategoriesListItemViewModel
    }

    private val _listItemState: MutableStateFlow<HealthCategoriesListItemState> =
      MutableStateFlow(
        HealthCategoriesListItemState.LOADING,
      )
    val listItemState = _listItemState.stateIn(viewModelScope, SharingStarted.Lazily, HealthCategoriesListItemState.LOADING)

    init {
      viewModelScope.launch(ioDispatcher) {
        val organizationsFlow =
          if (filterOrganization == null) {
            // If we do not want to filter on a specific organization, observe all stored organizations
            organizationRepository.storedOrganizationsFlow
          } else {
            // If we want to filter on a specific organization, filter on that one
            organizationRepository.storedOrganizationsFlow.map { organizations ->
              organizations.filter {
                it.id ==
                  filterOrganization.id
              }
            }
          }

        organizationsFlow.collectLatest { organizations ->
          // Always start with loading state whenever a organization has been added
          _listItemState.update { HealthCategoriesListItemState.LOADING }

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
            _listItemState.update { HealthCategoriesListItemState.NO_DATA }
          } else {
            // Observe the fhir responses
            combine(fhirResponseFlows) { responses -> responses.toList() }.collectLatest { responses ->
              val allEmpty = responses.filterIsInstance<FhirResponse.Success>().all { response -> response.isEmpty }
              if (allEmpty) {
                _listItemState.update { HealthCategoriesListItemState.NO_DATA }
              } else {
                _listItemState.update { HealthCategoriesListItemState.LOADED }
              }
            }
          }
        }
      }
    }
  }
