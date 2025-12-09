package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.banner

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.fhir.FhirResponseErrorType
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.GetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import javax.inject.Inject

class DefaultGetHealthCategoriesBanner
  @Inject
  constructor(
    private val organizationRepository: OrganizationRepository,
    private val getHealthCategoriesFromDisk: GetHealthCategoriesFromDisk,
    private val getEndpointsForHealthCategory: GetEndpointsForHealthCategory,
    private val fhirRepository: FhirRepository,
  ) : GetHealthCategoriesBanner {
    override operator fun invoke(): Flow<HealthCategoriesBannerState?> {
      val categories = getHealthCategoriesFromDisk.invoke().map { group -> group.categories }.flatten()

      // Get the total amount of fhir responses that are requested
      val totalAmountOfFhirResponses: Flow<Int> =
        organizationRepository.storedOrganizationsFlow.map { organizations ->
          organizations
            .flatMap { organization ->
              categories.flatMap { category ->
                getEndpointsForHealthCategory(category = category, organization = organization)
              }
            }.size
        }

      // Return correct banner based on the fhir responses
      return combine(totalAmountOfFhirResponses, fhirRepository.observe()) { totalAmount, fhirResponses ->
        if (fhirResponses.size == totalAmount) {
          val hasSuccessResponse = fhirResponses.any { it is FhirResponse.Success }
          when {
            fhirResponses.any { it is FhirResponse.Error && it.type == FhirResponseErrorType.USER } ->
              HealthCategoriesBannerState.Error.UserError(hasSuccessResponse)
            fhirResponses.any { it is FhirResponse.Error && it.type == FhirResponseErrorType.SERVER } ->
              HealthCategoriesBannerState.Error.ServerError(hasSuccessResponse)
            else -> null
          }
        } else {
          HealthCategoriesBannerState.Loading
        }
      }
    }
  }
