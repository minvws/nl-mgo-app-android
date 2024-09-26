package nl.rijksoverheid.mgo.data.healthcare

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.test.runTest

class DefaultHealthCareDataStateRepositoryTest {
    @Test
    fun `Given dummy organization and medication category, When calling get, Then loading and loaded states are emitted`() =
        runTest {
            // Given
            val uiSchemaRepository = TestUiSchemaRepository()
            val repository = DefaultHealthCareDataStateRepository(uiSchemaRepository)

            // When
            repository.get(organization = TEST_MGO_ORGANIZATION, category = HealthCareCategory.MEDICATIONS).test {
                // Then
                assertTrue(awaitItem().loading)
                assertFalse(awaitItem().loading)
                awaitComplete()
            }
        }
}
