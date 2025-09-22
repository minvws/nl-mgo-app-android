package nl.rijksoverheid.mgo.data.healthData.health

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.healthData.configuration.TestHealthDataConfigurationRepository
import nl.rijksoverheid.mgo.data.healthData.fhir.TestFhirDataRepository
import nl.rijksoverheid.mgo.data.healthData.health.models.HealthData
import nl.rijksoverheid.mgo.data.localisation.models.TEST_BGZ_DATA_SERVICE
import nl.rijksoverheid.mgo.data.localisation.models.TEST_GP_DATA_SERVICE
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.File

class DefaultHealthDataRepositoryTest {
  private val organizationRepository = TestOrganizationRepository()
  private val configurationRepository = TestHealthDataConfigurationRepository()
  private val fhirDataRepository = TestFhirDataRepository()

  private val repository =
    DefaultHealthDataRepository(
      organizationRepository = organizationRepository,
      configurationRepository = configurationRepository,
      fhirDataRepository = fhirDataRepository,
    )

  @Before
  fun setup() {
    val organization1 = TEST_MGO_ORGANIZATION.copy(id = "1", name = "Organization 1", dataServices = listOf(TEST_BGZ_DATA_SERVICE, TEST_GP_DATA_SERVICE))
    organizationRepository.setStoredProviders(listOf(organization1))
  }

  @Test
  fun testInit() =
    runTest {
      // When: Calling init
      repository.init()

      // Then: Loading states are emitted
      turbineScope {
        val healthDataForProblemsFlow = repository.observe("problems").testIn(backgroundScope)
        val healthDataForAlertsFlow = repository.observe("alerts").testIn(backgroundScope)
        val healthDataForProblems = healthDataForProblemsFlow.awaitItem()
        val healthDataForAlerts = healthDataForAlertsFlow.awaitItem()
        assertEquals(1, healthDataForProblems.size)
        assertTrue(healthDataForProblems.first() is HealthData.Loading)
        assertEquals(2, healthDataForAlerts.size)
        assertTrue(healthDataForAlerts[0] is HealthData.Loading)
        assertTrue(healthDataForAlerts[1] is HealthData.Loading)
      }
    }

  @Test
  fun testFetchProblemsSuccess() =
    runTest {
      // Given: Network requests are successful
      fhirDataRepository.setFetchResult(Result.success(File("")))

      // When: Fetching all data for the problems category
      repository.fetch("problems")

      // Then: Health data is emitted
      repository.observe("problems").test {
        val healthData = awaitItem()
        assertEquals(1, healthData.size)
        assertEquals("http://nictiz.nl/fhir/StructureDefinition/zib-Problem", healthData.first().profiles.first())
      }
    }

  @Test
  fun testFetchProblemsError() =
    runTest {
      // Given: Network requests are successful
      fhirDataRepository.setFetchResult(Result.failure(IllegalStateException("Something went wrong")))

      // When: Fetching all data for the problems category
      repository.fetch("problems")

      // Then: Health data is emitted
      repository.observe("problems").test {
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
      fhirDataRepository.setFetchResult(Result.success(File("")))

      // When: Fetching all data for the problems category
      repository.fetch("problems")

      // When: Fetching all data for the alerts category
      repository.fetch("alerts")

      // Then: Health data is emitted only for alerts
      repository.observe("alerts").test {
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
