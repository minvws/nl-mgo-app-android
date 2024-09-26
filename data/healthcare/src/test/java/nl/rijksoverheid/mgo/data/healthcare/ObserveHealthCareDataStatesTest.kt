package nl.rijksoverheid.mgo.data.healthcare

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.test.runTest

class ObserveHealthCareDataStatesTest {
    @Test
    fun `Given stored dummy organization, When calling invoke, Then health care data states are updated and emitted`() =
        runTest {
            turbineScope {
                // Given
                val organizationRepository = TestOrganizationRepository()
                organizationRepository.setStoredProviders(listOf(TEST_MGO_ORGANIZATION))
                val healthCareDataStatesRepository = TestHealthCareDataStatesRepository(initialData = listOf())
                healthCareDataStatesRepository.setRefreshData(listOf(TEST_HEALTH_CARE_DATA_STATE_LOADED))
                val observeHealthCareDataStates =
                    ObserveHealthCareDataStates(
                        organizationRepository = organizationRepository,
                        healthCareDataStatesRepository = healthCareDataStatesRepository,
                    )

                // When (wait until this flow is complete)
                observeHealthCareDataStates.invoke().testIn(backgroundScope).awaitItem()

                // Then
                healthCareDataStatesRepository.observe(category = HealthCareCategory.MEDICATIONS, filterOrganization = null).test {
                    val emit = awaitItem()
                    assertEquals(1, emit.size)
                    assertEquals(TEST_HEALTH_CARE_DATA_STATE_LOADED, emit.first())
                }
            }
        }
}
