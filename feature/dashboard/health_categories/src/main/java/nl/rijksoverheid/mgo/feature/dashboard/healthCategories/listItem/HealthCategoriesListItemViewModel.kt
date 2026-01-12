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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.fhir.GetRequests
import nl.rijksoverheid.mgo.component.fhir.ObserveFhirResponses
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import javax.inject.Named

@HiltViewModel(assistedFactory = HealthCategoriesListItemViewModel.Factory::class)
internal class HealthCategoriesListItemViewModel
  @AssistedInject
  constructor(
    @Assisted private val filterOrganization: MgoOrganization?,
    @Assisted private val category: HealthCategoryGroup.HealthCategory,
    private val getRequests: GetRequests,
    private val organizationRepository: OrganizationRepository,
    private val fhirRepository: FhirRepository,
    private val observeFhirResponses: ObserveFhirResponses,
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

        organizationsFlow
          .flatMapLatest { organizations ->

            _listItemState.update { HealthCategoriesListItemState.LOADING }

            val endpoints =
              getRequests(
                organizations = organizations,
                categories = listOf(category),
              )

            val fhirResponses =
              observeFhirResponses(
                categories = listOf(category),
                organizations = organizations,
              )

            fhirResponses.map { responses ->
              val isLoading = responses.size != endpoints.size
              val allEmpty =
                responses
                  .filterIsInstance<FhirResponse.Success>()
                  .all { it.isEmpty }
              val test1 = responses
              val test2 = endpoints
              test1
              test2

              when {
                isLoading -> HealthCategoriesListItemState.LOADING
                allEmpty -> HealthCategoriesListItemState.NO_DATA
                else -> HealthCategoriesListItemState.LOADED
              }
            }
          }.collectLatest { state ->
            _listItemState.update { state }
          }
      }
    }
  }
