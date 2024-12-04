package nl.rijksoverheid.mgo.feature.localisation.organizationSearch

import nl.rijksoverheid.mgo.data.localisation.models.TEST_BGZ_DATA_SERVICE
import nl.rijksoverheid.mgo.data.localisation.models.TEST_GP_DATA_SERVICE
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.localisation.models.TEST_NOT_IMPLEMENTED_DATA_SERVICE
import nl.rijksoverheid.mgo.feature.localisation.organizationList.OrganizationSearchCardState
import nl.rijksoverheid.mgo.feature.localisation.organizationList.getCardState
import org.junit.Assert.assertEquals
import org.junit.Test

internal class OrganizationSearchCardStateTest {
    @Test
    fun testAddedState() {
        // Given
        val organization = TEST_MGO_ORGANIZATION.copy(added = true)

        // When
        val state = organization.getCardState()

        // Then
        assertEquals(OrganizationSearchCardState.ADDED, state)
    }

    @Test
    fun testNotSupportedState() {
        // Given
        val organization = TEST_MGO_ORGANIZATION.copy(dataServices = listOf())

        // When
        val state = organization.getCardState()

        // Then
        assertEquals(OrganizationSearchCardState.NOT_SUPPORTED, state)
    }

    @Test
    fun testNotImplementedState() {
        // Given
        val organization = TEST_MGO_ORGANIZATION.copy(dataServices = listOf(TEST_NOT_IMPLEMENTED_DATA_SERVICE))

        // When
        val state = organization.getCardState()

        // Then
        assertEquals(OrganizationSearchCardState.NOT_SUPPORTED, state)
    }

    @Test
    fun testOneNotImplementedState() {
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
    fun testAddStateBgz() {
        // Given
        val organization = TEST_MGO_ORGANIZATION.copy(dataServices = listOf(TEST_BGZ_DATA_SERVICE))

        // When
        val state = organization.getCardState()

        // Then
        assertEquals(OrganizationSearchCardState.ADD, state)
    }

    @Test
    fun testAddStateGp() {
        // Given
        val organization = TEST_MGO_ORGANIZATION.copy(dataServices = listOf(TEST_GP_DATA_SERVICE))

        // When
        val state = organization.getCardState()

        // Then
        assertEquals(OrganizationSearchCardState.ADD, state)
    }

    @Test
    fun testAddStateBgzAndGp() {
        // Given
        val organization = TEST_MGO_ORGANIZATION.copy(dataServices = listOf(TEST_BGZ_DATA_SERVICE))

        // When
        val state = organization.getCardState()

        // Then
        assertEquals(OrganizationSearchCardState.ADD, state)
    }
}
