package nl.rijksoverheid.mgo.data.organization.api

interface OrganizationApiClient {
  fun getOrganizations(): Result<OrganizationApiResponse>

  fun getEndpoints(): Result<OrganizationApiResponse>
}
