package nl.rijksoverheid.mgo.component.error

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import nl.rijksoverheid.mgo.component.fhir.GetEndpoints
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.fhir.FhirResponseErrorType
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import javax.inject.Inject

class DefaultGetErrorBanner
  @Inject
  constructor(
    private val fhirRepository: FhirRepository,
    private val getEndpoints: GetEndpoints,
  ) : GetErrorBanner {
    override operator fun invoke(
      categories: List<HealthCategoryGroup.HealthCategory>,
      organizations: List<MgoOrganization>,
    ): Flow<ErrorBannerState?> {
      // All the endpoints that are requested
      val endpoints = getEndpoints(organizations = organizations, categories = categories)

      // The fhir responses that are returned
      val fhirResponses =
        fhirRepository.observe().map { responses ->
          responses.filter { response ->
            endpoints.any { endpoint ->
              endpoint.organization.id == response.request.organizationId &&
                endpoint.dataServiceId == response.request.dataServiceId &&
                endpoint.endpointId == response.request.endpointId
            }
          }
        }

      // Return correct banner based on the fhir responses
      return fhirResponses.map { responses ->
        val hasSuccessResponse = responses.any { it is FhirResponse.Success }
        when {
          endpoints.size != responses.size -> ErrorBannerState.Loading

          responses.any { it is FhirResponse.Error && it.type == FhirResponseErrorType.USER } ->
            ErrorBannerState.Error.UserError(hasSuccessResponse)

          responses.any { it is FhirResponse.Error && it.type == FhirResponseErrorType.SERVER } ->
            ErrorBannerState.Error.ServerError(hasSuccessResponse)

          else -> null
        }
      }
    }
  }
