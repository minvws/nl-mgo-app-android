package nl.rijksoverheid.mgo.data.organization

import nl.rijksoverheid.mgo.data.organization.api.OrganizationApiClient
import nl.rijksoverheid.mgo.data.organization.api.OrganizationApiResponse
import nl.rijksoverheid.mgo.framework.test.getResource

class TestOrganizationApiClient : OrganizationApiClient {
  private var organizationsResult =
    Result.success(
      OrganizationApiResponse(
        response = getResource("organizations.json"),
        cached = false,
      ),
    )
  private var endpointsResult =
    Result.success(
      OrganizationApiResponse(
        response = getResource("endpoints.json"),
        cached = false,
      ),
    )

  override fun getOrganizations(): Result<OrganizationApiResponse> = organizationsResult

  fun setOrganizationsResult(result: Result<OrganizationApiResponse>) {
    organizationsResult = result
  }

  override fun getEndpoints(): Result<OrganizationApiResponse> = endpointsResult

  fun setEndpointsResult(result: Result<OrganizationApiResponse>) {
    endpointsResult = result
  }
}
