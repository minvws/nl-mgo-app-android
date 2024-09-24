package nl.rijksoverheid.mgo.data.healthcare

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.test.runTest

class DefaultHealthCareStateRepositoryTest {
    @Test
    fun `Given stored organizations, When creating repository and observing to medications, Then correct states are emitted`() =
        runTest {
            // Given
            val healthCareRepository = TestHealthCareRepository()
            val organizationRepository = TestOrganizationRepository()
            val repository =
                DefaultHealthCareStateRepository(
                    healthCareRepository = healthCareRepository,
                    organizationRepository = organizationRepository,
                )

            repository.observe(category = HealthCareCategory.MEDICATIONS, organization = null).distinctUntilChanged().test {
                // When
                repository.init(organizations = listOf(TEST_MGO_ORGANIZATION))

                // Then

                // Skip first item since it's an empty list
                skipItems(1)

                // First emit are loading states
                val emit1 = awaitItem()
                assertEquals(1, emit1.size)
                assertEquals(true, emit1.first().loading)

                // Second emit is loaded state
                val emit2 = awaitItem()
                assertEquals(1, emit2.size)
                assertEquals(false, emit2.first().loading)
            }
        }

    @Test
    fun `Given stored organizations, When creating repository and observing to organization, Then correct states are emitted`() =
        runTest {
            // Given
            val healthCareRepository = TestHealthCareRepository()
            val organizationRepository = TestOrganizationRepository()
            val repository =
                DefaultHealthCareStateRepository(
                    healthCareRepository = healthCareRepository,
                    organizationRepository = organizationRepository,
                )

            repository.observe(
                category = HealthCareCategory.MEDICATIONS,
                organization = TEST_MGO_ORGANIZATION,
            ).distinctUntilChanged().test {
                // When
                repository.init(organizations = listOf(TEST_MGO_ORGANIZATION))

                // Then

                // Skip first item since it's an empty list
                skipItems(1)

                // First emit are loading states
                val emit1 = awaitItem()
                assertEquals(1, emit1.size)
                assertEquals(true, emit1.first().loading)

                // Second emit is loaded state
                val emit2 = awaitItem()
                assertEquals(1, emit2.size)
                assertEquals(false, emit2.first().loading)
            }
        }
}
