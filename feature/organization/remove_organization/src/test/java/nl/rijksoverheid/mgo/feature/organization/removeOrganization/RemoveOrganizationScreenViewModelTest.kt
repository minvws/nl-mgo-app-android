package nl.rijksoverheid.mgo.feature.organization.removeOrganization

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class RemoveOrganizationScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val organizationRepository =
        TestOrganizationRepository()

    @Test
    fun `Given a stored health care provider, When deleting that health care provider, ui is notified that provider is deleted`() =
        runTest {
            val viewModel =
                RemoveOrganizationScreenViewModel(
                    organizationRepository = organizationRepository,
                )
            viewModel.providerDeleted.test {
                // Given
                organizationRepository.setStoredProviders(providers = listOf(TEST_MGO_ORGANIZATION))

                // When
                viewModel.delete(TEST_MGO_ORGANIZATION.id)

                // Then
                assertEquals(Unit, awaitItem())
            }
        }
}
