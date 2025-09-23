package nl.rijksoverheid.mgo.data.healthData.health

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.healthData.configuration.TestHealthDataConfigurationRepository
import nl.rijksoverheid.mgo.data.healthData.configuration.models.TEST_HEALTH_CATEGORY_ALERTS
import nl.rijksoverheid.mgo.data.healthData.configuration.models.TEST_HEALTH_CATEGORY_PROBLEMS
import nl.rijksoverheid.mgo.data.healthData.fhir.TestFhirDataRepository
import nl.rijksoverheid.mgo.data.healthData.health.models.HealthData
import nl.rijksoverheid.mgo.data.localisation.models.TEST_BGZ_DATA_SERVICE
import nl.rijksoverheid.mgo.data.localisation.models.TEST_GP_DATA_SERVICE
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestCacheFileStore
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class DefaultHealthDataRepositoryTest {
  private val cacheFileStore = TestCacheFileStore()
  private val configurationRepository = TestHealthDataConfigurationRepository()
  private val fhirDataRepository = TestFhirDataRepository()
  private val organization = TEST_MGO_ORGANIZATION.copy(id = "1", name = "Organization 1", dataServices = listOf(TEST_BGZ_DATA_SERVICE, TEST_GP_DATA_SERVICE))

  private val repository =
    DefaultHealthDataRepository(
      configurationRepository = configurationRepository,
      fhirDataRepository = fhirDataRepository,
      cacheFileStore = cacheFileStore,
    )

  @Test
  fun testFetchProblemsCached() =
    runTest {
      // Given: Fhir response is cached
      cacheFileStore.setFile(File(""))

      // When: Fetching all data for the problems category
      repository.fetch(category = TEST_HEALTH_CATEGORY_PROBLEMS, organization = organization)

      // Then: Health data is emitted
      repository.observe(TEST_HEALTH_CATEGORY_PROBLEMS).test {
        val healthData = awaitItem()
        assertEquals(1, healthData.size)
        assertTrue(healthData.first() is HealthData.Success)
        assertEquals("http://nictiz.nl/fhir/StructureDefinition/zib-Problem", healthData.first().profiles.first())
      }
    }

  @Test
  fun testFetchProblemsSuccess() =
    runTest {
      // Given: Network requests are successful
      fhirDataRepository.setFetchResult(Result.success("{}".toResponseBody("application/json".toMediaType())))

      // When: Fetching all data for the problems category
      repository.fetch(category = TEST_HEALTH_CATEGORY_PROBLEMS, organization = organization)

      // Then: Health data is emitted
      repository.observe(TEST_HEALTH_CATEGORY_PROBLEMS).test {
        val healthData = awaitItem()
        assertEquals(1, healthData.size)
        assertTrue(healthData.first() is HealthData.Success)
        assertEquals("http://nictiz.nl/fhir/StructureDefinition/zib-Problem", healthData.first().profiles.first())
      }
    }

  @Test
  fun testFetchProblemsError() =
    runTest {
      // Given: Network requests are successful
      fhirDataRepository.setFetchResult(Result.failure(IllegalStateException("Something went wrong")))

      // When: Fetching all data for the problems category
      repository.fetch(category = TEST_HEALTH_CATEGORY_PROBLEMS, organization = organization)

      // Then: Health data is emitted
      repository.observe(category = TEST_HEALTH_CATEGORY_PROBLEMS).test {
        val healthData = awaitItem()
        assertEquals(1, healthData.size)
        assertTrue(healthData.first() is HealthData.Error)
        assertEquals("http://nictiz.nl/fhir/StructureDefinition/zib-Problem", healthData.first().profiles.first())
      }
    }

  @Test
  fun testFetchAlertsSuccess() =
    runTest {
      // Given: Network requests are successful
      fhirDataRepository.setFetchResult(Result.success("{}".toResponseBody("application/json".toMediaType())))

      // When: Fetching all data for the problems category
      repository.fetch(category = TEST_HEALTH_CATEGORY_PROBLEMS, organization = organization)

      // When: Fetching all data for the alerts category
      repository.fetch(category = TEST_HEALTH_CATEGORY_ALERTS, organization = organization)

      // Then: Health data is emitted only for alerts
      repository.observe(TEST_HEALTH_CATEGORY_ALERTS).test {
        val healthData = awaitItem()
        assertEquals(2, healthData.size)
        assertTrue(healthData[0] is HealthData.Success)
        assertTrue(healthData[1] is HealthData.Success)
        assertEquals("http://nictiz.nl/fhir/StructureDefinition/zib-Alert", healthData[0].profiles[0])
        assertEquals("http://nictiz.nl/fhir/StructureDefinition/zib-Alert", healthData[1].profiles[0])
        assertEquals("http://fhir.nl/fhir/StructureDefinition/nl-core-episodeofcare", healthData[1].profiles[1])
      }
    }
}
