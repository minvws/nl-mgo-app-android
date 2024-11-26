package nl.rijksoverheid.mgo.data.healthcare

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.test.runTest

class DefaultHealthCareDataStatesRepositoryTest {
    @Test
    fun `Given dummy organization and medication category, When observing organization and category, Then state is emitted`() =
        runTest {
            // Given: Refresh organization with medication category
            val healthCareDataStateRepository = TestHealthCareDataStateRepository()
            val repository = DefaultHealthCareDataStatesRepository(healthCareDataStateRepository = healthCareDataStateRepository)
            repository.refresh(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategory.MEDICATIONS)

            // When: Observing state for organization with category
            repository.observe(category = HealthCareCategory.MEDICATIONS, filterOrganization = TEST_MGO_ORGANIZATION).test {
                // Then: Expect our state
                val emit = awaitItem()
                assertEquals(1, emit.size)
                assertEquals(HealthCareCategory.MEDICATIONS, emit.first().category)
            }
        }

    @Test
    fun `Given multiple organizations, When observing category, Then state is emitted`() =
        runTest {
            // Given: Refresh organization with multiple categories
            val healthCareDataStateRepository = TestHealthCareDataStateRepository()
            healthCareDataStateRepository
            val repository = DefaultHealthCareDataStatesRepository(healthCareDataStateRepository = healthCareDataStateRepository)
            repository.refresh(organization = TEST_MGO_ORGANIZATION.copy(id = "1", name = "bla"), category = HealthCareCategory.MEDICATIONS)
            repository.refresh(
                organization = TEST_MGO_ORGANIZATION.copy(id = "2", name = "bla2"),
                category =
                    HealthCareCategory
                        .MEDICATIONS,
            )
            repository.refresh(
                organization = TEST_MGO_ORGANIZATION.copy(id = "3", name = "bla3"),
                category =
                    HealthCareCategory
                        .MEDICATIONS,
            )

            // When: Observing state for all medications
            repository.observe(category = HealthCareCategory.MEDICATIONS, filterOrganization = null).test {
                // Then: Expect our organizations
                val emit = awaitItem()
                assertEquals(3, emit.size)
                assertEquals("1", emit[0].organization.id)
                assertEquals("2", emit[1].organization.id)
                assertEquals("3", emit[2].organization.id)
            }
        }

    @Test
    fun `Given dummy organization is present, When calling delete, Then state is emitted`() =
        runTest {
            // Given: Dummy organization is present with medications and appointments data
            val healthCareDataStateRepository = TestHealthCareDataStateRepository()
            val repository = DefaultHealthCareDataStatesRepository(healthCareDataStateRepository = healthCareDataStateRepository)
            repository.refresh(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategory.MEDICATIONS)
            repository.refresh(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategory.APPOINTMENTS)

            // When: Deleting the organization
            repository.delete(TEST_MGO_ORGANIZATION)

            // Then: Nothing is expected when observing that organization since it's removed
            repository.observe(category = HealthCareCategory.MEDICATIONS, filterOrganization = TEST_MGO_ORGANIZATION).test {
                expectNoEvents()
            }
        }
}
