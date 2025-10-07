package nl.rijksoverheid.mgo.init

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.GetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

class FhirResponseSyncer
  @Inject
  constructor(
    private val organizationRepository: OrganizationRepository,
    private val getHealthCategoriesFromDisk: GetHealthCategoriesFromDisk,
    private val fhirRepository: FhirRepository,
    private val getEndpointsForHealthCategory: GetEndpointsForHealthCategory,
    @Named("dvaApiBaseUrl") private val dvaApiBaseUrl: String,
  ) {
    operator fun invoke(): Flow<List<MgoOrganization>> =
      organizationRepository.storedOrganizationsFlow.onEach { organizations ->
        for (organization in organizations) {
          organization.fetchFhirResponses()
        }
      }

    private suspend fun MgoOrganization.fetchFhirResponses() {
      val categories = getHealthCategoriesFromDisk.invoke().map { group -> group.categories }.flatten()
      val dataServices = dataServices.map { dataService -> dataService }
      for (category in categories) {
        val endpointsWithDataSet = getEndpointsForHealthCategory(category = category, filterDataSetIds = dataServices.map { it.id })

        for (endpointWithDataSet in endpointsWithDataSet) {
          for (endpoint in endpointWithDataSet.endpoints) {
            for (dataService in dataServices) {
              Timber.v("Data service: " + dataService.id)
              fhirRepository.fetch(
                organizationId = id,
                dataServiceId = dataService.id,
                endpointId = endpoint.id,
                resourceEndpoint = dataService.resourceEndpoint,
                fhirVersion = FhirVersion.valueOf(endpointWithDataSet.dataSet.fhirVersion),
                url = "$dvaApiBaseUrl/fhir${endpoint.url}",
              )
            }
          }
        }
      }
    }
  }
