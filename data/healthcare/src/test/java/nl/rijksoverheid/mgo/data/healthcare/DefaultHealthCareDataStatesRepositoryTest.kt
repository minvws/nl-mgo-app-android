package nl.rijksoverheid.mgo.data.healthcare

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.test.runTest

class DefaultHealthCareDataStatesRepositoryTest {
    @Test
    fun `Given dummy organization and medication category, When calling refresh, Then state is emitted`() =
        runTest {
            // Given
            val healthCareDataStateRepository = TestHealthCareDataStateRepository()
            val repository = DefaultHealthCareDataStatesRepository(healthCareDataStateRepository = healthCareDataStateRepository)

            // When
            repository.refresh(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategory.MEDICATIONS)

            // Then
            repository.observe(category = HealthCareCategory.MEDICATIONS, filterOrganization = TEST_MGO_ORGANIZATION).test {
                val emit = awaitItem()
                assertEquals(1, emit.size)
                assertEquals(HealthCareCategory.MEDICATIONS, emit.first().category)
            }
        }

    @Test
    fun `Given medication category, When calling refresh twice, Then two states are emitted`() =
        runTest {
            // Given
            val healthCareDataStateRepository = TestHealthCareDataStateRepository()
            val repository = DefaultHealthCareDataStatesRepository(healthCareDataStateRepository = healthCareDataStateRepository)

            // When
            repository.refresh(organization = TEST_MGO_ORGANIZATION.copy(id = "1"), category = HealthCareCategory.MEDICATIONS)
            repository.refresh(organization = TEST_MGO_ORGANIZATION.copy(id = "2"), category = HealthCareCategory.MEDICATIONS)

            // Then
            repository.observe(category = HealthCareCategory.MEDICATIONS, filterOrganization = null).test {
                val emit = awaitItem()
                assertEquals(2, emit.size)
                assertEquals(HealthCareCategory.MEDICATIONS, emit[0].category)
                assertEquals(HealthCareCategory.MEDICATIONS, emit[1].category)
            }
        }
}
