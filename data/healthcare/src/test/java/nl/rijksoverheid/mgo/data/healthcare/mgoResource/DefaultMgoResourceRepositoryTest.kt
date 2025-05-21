package nl.rijksoverheid.mgo.data.healthcare.mgoResource

import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.api.dva.createDvaApi
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.TEST_MGO_RESOURCE
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.TestMgoResourceMapper
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.TEST_HEALTH_CARE_DATA_STATE_LOADED
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.TestHealthCareDataStatesStore
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.urlCreator.TestHealthCareUrlCreator
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class DefaultMgoResourceRepositoryTest {
  @get:Rule
  val testServerRule = TestServerRule()

  private val testServer = testServerRule.testServer
  private val healthCareDataStatesStore = TestHealthCareDataStatesStore()

  @Test
  fun testGetResourcesSuccess() =
    runTest {
      // Given: Repository
      val repository = createRepository()

      // Given: Successful request
      testServer.enqueue200(amount = 1)

      // When: Calling get
      val result = repository.get(endpoint = "", request = HealthCareRequest.Bgz.MedicationUse)

      // Then: Results are success
      assertTrue(result.isSuccess)
    }

  @Test
  fun testGetResourcesFailed() =
    runTest {
      // Given: Repository
      val repository = createRepository()

      // Given: Failed request
      testServer.enqueue500(amount = 1)

      // When: Calling get
      val result = repository.get(endpoint = "", request = HealthCareRequest.Bgz.MedicationUse)

      // Then: Results are success
      assertTrue(result.isFailure)
    }

  @Test
  fun testGetResourceSuccess() =
    runTest {
      // Given: Repository
      val repository = createRepository()

      // Given: Resource exists in store
      healthCareDataStatesStore.add(
        organization = TEST_MGO_ORGANIZATION,
        category = HealthCareCategory.MEDICATIONS,
        state = TEST_HEALTH_CARE_DATA_STATE_LOADED,
      )

      // When: Getting resource with id 1
      val result = repository.get("1")

      // Then: Resource is returned from store
      assertEquals(TEST_MGO_RESOURCE, result.getOrNull())
    }

  @Test
  fun testGetResourceFailed() =
    runTest {
      // Given: Repository
      val repository = createRepository()

      // When: Getting resource with id 1
      val result = repository.get("1")

      // Then: Resource is not returned from store
      assertEquals(null, result.getOrNull())
    }

  private fun createRepository(): DefaultMgoResourceRepository {
    return DefaultMgoResourceRepository(
      healthCareDataStatesStore = healthCareDataStatesStore,
      dvaApi = createDvaApi(okHttpClient = TEST_OKHTTP_CLIENT, baseUrl = testServer.url()),
      urlCreator = TestHealthCareUrlCreator(),
      dvaApiBaseUrl = "",
      mgoResourceMapper = TestMgoResourceMapper(),
    )
  }
}
