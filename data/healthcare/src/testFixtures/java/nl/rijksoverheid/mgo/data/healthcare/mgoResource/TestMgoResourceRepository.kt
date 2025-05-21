package nl.rijksoverheid.mgo.data.healthcare.mgoResource

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.TEST_MGO_RESOURCE

class TestMgoResourceRepository : MgoResourceRepository {
  private var mgoResource: Result<MgoResource> = Result.success(TEST_MGO_RESOURCE)
  private var mgoResources: Result<List<MgoResource>> = Result.success(listOf())
  private var mgoResourcesFiltered: List<MgoResource> = listOf()

  fun setMgoResource(resource: Result<MgoResource>) {
    this.mgoResource = resource
  }

  fun setMgoResources(resources: Result<List<MgoResource>>) {
    this.mgoResources = resources
  }

  fun setMgoResourcesFiltered(resources: List<MgoResource>) {
    this.mgoResourcesFiltered = resources
  }

  override suspend fun get(
    endpoint: String,
    request: HealthCareRequest,
  ): Result<List<MgoResource>> {
    return mgoResources
  }

  override suspend fun get(referenceId: String): Result<MgoResource> {
    return mgoResource
  }

  override suspend fun filter(
    resources: List<MgoResource>,
    profiles: List<String>,
  ): List<MgoResource> {
    return mgoResourcesFiltered
  }
}
