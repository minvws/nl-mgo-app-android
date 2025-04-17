package nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates

import app.cash.turbine.turbineScope
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Test

class CollectHealthCareDataStatesTest {
  @Test
  fun testRefreshingAndDeleting() =
    runTest {
      turbineScope {
        // Given: Some dummy organizations
        val dummyProvider1 = TEST_MGO_ORGANIZATION.copy(id = "1")
        val dummyProvider2 = TEST_MGO_ORGANIZATION.copy(id = "2")
        val dummyProvider3 = TEST_MGO_ORGANIZATION.copy(id = "3")
        val dummyProvider4 = TEST_MGO_ORGANIZATION.copy(id = "4")

        // Given: Dependencies
        val healthCareDataStatesRepository = mockk<HealthCareDataStatesRepository>(relaxed = true)
        val organizationRepository = TestOrganizationRepository()

        // Given: The use case
        val usecase =
          CollectHealthCareDataStates(
            organizationRepository = organizationRepository,
            healthCareDataStatesRepository = healthCareDataStatesRepository,
          )

        // Given: Our previous organizations state was provider 1 and 4
        usecase.previousStoredOrganizations = listOf(dummyProvider1, dummyProvider4)

        // Given: Our new organizations state is provider 1, 2 and 3
        organizationRepository.setStoredProviders(listOf(dummyProvider1, dummyProvider2, dummyProvider3))

        // When: Call use case so it's starts observing the organizations
        usecase.invoke().testIn(backgroundScope).awaitItem()

        // Then: Dummy provider 3 is new, so that is refreshed
        coVerify {
          healthCareDataStatesRepository.refresh(
            organization = dummyProvider3,
            category = HealthCareCategory.MEDICATIONS,
          )
        }

        // Then: Dummy provider 4 is removed, so that is removed
        coVerify { healthCareDataStatesRepository.delete(organization = dummyProvider4) }
      }
    }
}
