package nl.rijksoverheid.mgo.data.healthData.health

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.healthData.configuration.TestHealthDataConfigurationRepository
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Assert.assertEquals
import org.junit.Test

class DefaultInitHealthDataFetchingTest {
  private val organizationRepository = TestOrganizationRepository()
  private val healthDataRepository = TestHealthDataRepository()
  private val healthDataConfigurationRepository = TestHealthDataConfigurationRepository()
  private val usecase = DefaultInitHealthDataFetching(organizationRepository, healthDataRepository, healthDataConfigurationRepository)

  @Test
  fun testInvoke() =
    runTest {
      // Given: One stored organization
      organizationRepository.setStoredProviders(listOf(TEST_MGO_ORGANIZATION))

      // When: Calling invoke
      usecase.invoke().test {
        awaitItem()

        // Then: Health data is fetched
        assertEquals(2, healthDataRepository.getFetchCalledAmount())
      }
    }
}
