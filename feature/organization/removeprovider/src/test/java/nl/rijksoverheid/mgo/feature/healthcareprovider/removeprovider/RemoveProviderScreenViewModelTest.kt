package nl.rijksoverheid.mgo.feature.healthcareprovider.removeprovider

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class RemoveProviderScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val healthCareProviderRepository =
        TestOrganizationRepository()

    @Test
    fun `Given a stored health care provider, When deleting that health care provider, ui is notified that provider is deleted`() =
        runTest {
            val viewModel =
                RemoveProviderScreenViewModel(
                    organizationRepository = healthCareProviderRepository,
                )
            viewModel.providerDeleted.test {
                // Given
                healthCareProviderRepository.setStoredProviders(providers = listOf(TEST_MGO_ORGANIZATION))

                // When
                viewModel.delete(TEST_MGO_ORGANIZATION.id)

                // Then
                Assert.assertEquals(Unit, awaitItem())
            }
        }
}
