package nl.rijksoverheid.mgo.data.healthcare

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.api.dva.createDvaApi
import nl.rijksoverheid.mgo.data.localisation.models.TEST_BGZ_DATA_SERVICE
import nl.rijksoverheid.mgo.data.localisation.models.TEST_GP_DATA_SERVICE
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.uiSchema.TestUiSchemaMapper
import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.TestServerRule
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.test.runTest

class DefaultHealthCareStateRepositoryTest {
    @get:Rule
    val testServerRule = TestServerRule()

    private val testServer = testServerRule.testServer

    @Test
    fun `Given one organization stored, When calling init, Then correct states are emitted`() =
        runTest {
            // Given
            val repository = createRepository()
            testServer.enqueueJsons(
                "bgzMedicationUseResponse.json",
                "bgzMedicationAgreementResponse.json",
                "bgzAdministrationAgreementResponse.json",
                "gpMedicationRequestResponse.json",
            )

            // When
            val organization = TEST_MGO_ORGANIZATION.copy(dataServices = listOf(TEST_BGZ_DATA_SERVICE, TEST_GP_DATA_SERVICE))
            repository.init(organizations = listOf(organization))

            // Then
            repository.observe(category = HealthCareCategory.MEDICATIONS, organization = null).distinctUntilChanged().test {
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
                assertFalse(emit2.first().uiSchemaListResults.first().isFailure)
            }
        }

    @Test
    fun `Given one organization stored, When calling init fails and refresh success, Then correct states are emitted`() =
        runTest {
            // Given
            val repository = createRepository()
            testServer.enqueue500(amount = 4)
            testServer.enqueueJsons(
                "bgzMedicationUseResponse.json",
                "bgzMedicationAgreementResponse.json",
                "bgzAdministrationAgreementResponse.json",
                "gpMedicationRequestResponse.json",
            )

            repository.observe(category = HealthCareCategory.MEDICATIONS, organization = null).distinctUntilChanged().test {
                // When
                val organization = TEST_MGO_ORGANIZATION.copy(dataServices = listOf(TEST_BGZ_DATA_SERVICE, TEST_GP_DATA_SERVICE))
                repository.init(organizations = listOf(organization))
                repository.refresh(category = HealthCareCategory.MEDICATIONS, filterOrganization = organization)

                // Then

                // Skip first item since it's an empty list
                skipItems(1)

                // First emit are loading states
                val emit1 = awaitItem()
                assertEquals(true, emit1.first().loading)

                // Second emit are loaded states
                val emit2 = awaitItem()
                assertEquals(false, emit2.first().loading)
                assertTrue(emit2.first().uiSchemaListResults.all { it.isFailure })

                // Third emit are loading states
                val emit3 = awaitItem()
                assertEquals(true, emit3.first().loading)

                // Fourth emit are loaded state
                val emit4 = awaitItem()
                assertEquals(false, emit4.first().loading)
                assertTrue(emit4.first().uiSchemaListResults.none { it.isFailure })
            }
        }

    private fun createRepository(): DefaultHealthCareStateRepository {
        val healthCareRepository =
            DefaultHealthCareRepository(
                uiSchemaMapper = TestUiSchemaMapper(listOf()),
                dvaApi = createDvaApi(okHttpClient = TEST_OKHTTP_CLIENT, baseUrl = testServer.url()),
                dvaApiBaseUrl = "",
            )
        val organizationRepository = TestOrganizationRepository()
        return DefaultHealthCareStateRepository(
            healthCareRepository = healthCareRepository,
            organizationRepository = organizationRepository,
        )
    }
}
