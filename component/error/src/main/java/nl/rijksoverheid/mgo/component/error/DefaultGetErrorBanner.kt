package nl.rijksoverheid.mgo.component.error

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import nl.rijksoverheid.mgo.component.fhir.GetRequests
import nl.rijksoverheid.mgo.component.fhir.ObserveFhirResponses
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.fhir.FhirResponseErrorType
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import timber.log.Timber
import javax.inject.Inject

class DefaultGetErrorBanner
  @Inject
  constructor(
    private val getRequests: GetRequests,
    private val observeFhirResponses: ObserveFhirResponses,
  ) : GetErrorBanner {
    override operator fun invoke(
      categories: List<HealthCategoryGroup.HealthCategory>,
      organizations: List<MgoOrganization>,
    ): Flow<ErrorBannerState?> {
      // Get endpoints that are requested
      val endpoints = getRequests(organizations = organizations, categories = categories)

      // Get fhir responses
      val fhirResponses = observeFhirResponses(categories = categories, organizations = organizations)

      // Return correct banner based on the fhir responses
      return fhirResponses.map { responses ->
        if (endpoints.size == 90) {
          Timber.v("Ik kom hier bart #1: " + responses.size)
        } else if (endpoints.size == 28) {
          Timber.v("Ik kom hier bart #2: " + responses.size)
        }
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
