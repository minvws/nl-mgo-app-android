package nl.rijksoverheid.mgo.feature.dashboard

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.feature.dashboard.overview.OverviewScreenViewModel
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class OverviewScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val organizationRepository = TestOrganizationRepository()

    @Test
    fun `Given stored providers, When collecting on view state, Then emit view state with providers`() =
        runTest {
            // Given
            organizationRepository.setStoredProviders(listOf(TEST_MGO_ORGANIZATION))

            // When
            val viewModel = OverviewScreenViewModel(organizationRepository = organizationRepository)
            viewModel.viewState.test {
                // Then
                assertEquals(listOf(TEST_MGO_ORGANIZATION), awaitItem().providers)
            }
        }
}
