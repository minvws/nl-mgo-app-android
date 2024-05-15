package nl.rijksoverheid.mgo.feature.dashboard.overview

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.localisation.TestHealthCareProviderRepository
import nl.rijksoverheid.mgo.data.localisation.models.TEST_HEALTH_CARE_PROVIDER
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class OverviewScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val healthCareProviderRepository = TestHealthCareProviderRepository()

    @Test
    fun `Given stored providers, When collecting on view state, Then emit view state with providers`() =
        runTest {
            // Given
            healthCareProviderRepository.setStoredProviders(listOf(TEST_HEALTH_CARE_PROVIDER))

            // When
            val viewModel = OverviewScreenViewModel(healthCareProviderRepository = healthCareProviderRepository)
            viewModel.viewState.test {
                // Then
                assertEquals(listOf(TEST_HEALTH_CARE_PROVIDER), awaitItem().providers)
            }
        }
}
