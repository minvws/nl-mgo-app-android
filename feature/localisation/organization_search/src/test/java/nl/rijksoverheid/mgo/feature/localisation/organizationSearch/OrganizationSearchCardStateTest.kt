package nl.rijksoverheid.mgo.feature.localisation.organizationSearch

import nl.rijksoverheid.mgo.data.localisation.models.TEST_BGZ_DATA_SERVICE
import nl.rijksoverheid.mgo.data.localisation.models.TEST_GP_DATA_SERVICE
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.localisation.models.TEST_NOT_IMPLEMENTED_DATA_SERVICE
import org.junit.Assert.assertEquals
import org.junit.Test

internal class OrganizationSearchCardStateTest {
    @Test
    fun `Given organization added, When calling getCardState, Then return correct state`() {
        // Given
        val organization = TEST_MGO_ORGANIZATION.copy(added = true)

        // When
        val state = organization.getCardState()

        // Then
        assertEquals(OrganizationSearchCardState.ADDED, state)
    }

    @Test
    fun `Given organization data services are empty, When calling getCardState, Then return correct state`() {
        // Given
        val organization = TEST_MGO_ORGANIZATION.copy(dataServices = listOf())

        // When
        val state = organization.getCardState()

        // Then
        assertEquals(OrganizationSearchCardState.NOT_SUPPORTED, state)
    }

    @Test
    fun `Given organization data services only contains not implemented, When calling getCardState, Then return correct state`() {
        // Given
        val organization = TEST_MGO_ORGANIZATION.copy(dataServices = listOf(TEST_NOT_IMPLEMENTED_DATA_SERVICE))

        // When
        val state = organization.getCardState()

        // Then
        assertEquals(OrganizationSearchCardState.NOT_SUPPORTED, state)
    }

    @Test
    fun `Given organization data services contains one not implemented, When calling getCardState, Then return correct state`() {
        // Given
        val organization =
            TEST_MGO_ORGANIZATION.copy(
                dataServices =
                    listOf(
                        TEST_BGZ_DATA_SERVICE,
                        TEST_GP_DATA_SERVICE,
                        TEST_NOT_IMPLEMENTED_DATA_SERVICE,
                    ),
            )

        // When
        val state = organization.getCardState()

        // Then
        assertEquals(OrganizationSearchCardState.ADD, state)
    }

    @Test
    fun `Given organization data services contains bgz, When calling getCardState, Then return correct state`() {
        // Given
        val organization = TEST_MGO_ORGANIZATION.copy(dataServices = listOf(TEST_BGZ_DATA_SERVICE))

        // When
        val state = organization.getCardState()

        // Then
        assertEquals(OrganizationSearchCardState.ADD, state)
    }

    @Test
    fun `Given organization data services contains gp, When calling getCardState, Then return correct state`() {
        // Given
        val organization = TEST_MGO_ORGANIZATION.copy(dataServices = listOf(TEST_GP_DATA_SERVICE))

        // When
        val state = organization.getCardState()

        // Then
        assertEquals(OrganizationSearchCardState.ADD, state)
    }

    @Test
    fun `Given organization data services contains bgz and gp, When calling getCardState, Then return correct state`() {
        // Given
        val organization = TEST_MGO_ORGANIZATION.copy(dataServices = listOf(TEST_BGZ_DATA_SERVICE))

        // When
        val state = organization.getCardState()

        // Then
        assertEquals(OrganizationSearchCardState.ADD, state)
    }
}
