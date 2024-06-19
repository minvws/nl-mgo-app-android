package nl.rijksoverheid.mgo.feature.healthcareprovider.removeprovider

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.localisation.models.TEST_HEALTH_CARE_PROVIDER
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.localisation.TestHealthCareProviderRepository
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class RemoveProviderScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val healthCareProviderRepository =
        TestHealthCareProviderRepository()

    @Test
    fun `Given a stored health care provider, When deleting that health care provider, ui is notified that provider is deleted`() =
        runTest {
            val viewModel =
                RemoveProviderScreenViewModel(
                    healthCareProviderRepository = healthCareProviderRepository,
                )
            viewModel.providerDeleted.test {
                // Given
                healthCareProviderRepository.setStoredProviders(providers = listOf(TEST_HEALTH_CARE_PROVIDER))

                // When
                viewModel.delete(TEST_HEALTH_CARE_PROVIDER.id)

                // Then
                Assert.assertEquals(Unit, awaitItem())
            }
        }
}
