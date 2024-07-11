package nl.rijksoverheid.mgo.feature.localisation.organizationList

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.localisation.models.TEST_HEALTH_CARE_PROVIDER
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.localisation.TestHealthCareProviderRepository
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class OrganizationListScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val healthCareProviderRepository =
        TestHealthCareProviderRepository()

    @Test
    fun `Given a stored organization, When deleting that organization, view state is updated with no providers`() =
        runTest {
            // Given
            healthCareProviderRepository.setStoredProviders(providers = listOf(TEST_HEALTH_CARE_PROVIDER))
            val viewModel =
                OrganizationListScreenViewModel(
                    healthCareProviderRepository = healthCareProviderRepository,
                )

            // When
            viewModel.delete(TEST_HEALTH_CARE_PROVIDER)

            // Then
            viewModel.viewState.test {
                Assert.assertEquals(0, awaitItem().providers.size)
            }
        }
}
