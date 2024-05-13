package nl.rijksoverheid.mgo.feature.localisation.searchresults

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.localisation.TestHealthCareProviderRepository
import nl.rijksoverheid.mgo.data.localisation.models.TEST_HEALTH_CARE_PROVIDER
import nl.rijksoverheid.mgo.feature.localisation.navigation.LocalisationNavigationScreen
import nl.rijksoverheid.mgo.framework.environment.AppFlavor
import nl.rijksoverheid.mgo.framework.environment.AppInfo
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.lang.IllegalStateException
import kotlinx.coroutines.test.runTest

internal class SearchResultsScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val healthCareProviderRepository = TestHealthCareProviderRepository()
    private val savedStateHandle =
        SavedStateHandle().apply {
            set("name", "Tandarts")
            set("city", "Roermond")
        }
    private val appInfo = AppInfo(versionCode = 1, appFlavor = AppFlavor.PROD)

    @After
    fun cleanUp() {
        healthCareProviderRepository.resetSearchResults()
    }

    @Test
    fun `Given search results call success, When getting search results, Then emit correct view state`() =
        runTest {
            // Given
            val viewModel =
                SearchResultsScreenViewModel(
                    savedStateHandle = savedStateHandle,
                    appInfo = appInfo,
                    healthCareProviderRepository = healthCareProviderRepository,
                )
            healthCareProviderRepository.setSearchResults(listOf(TEST_HEALTH_CARE_PROVIDER))

            viewModel.viewState.test {
                // When
                viewModel.getSearchResults()

                // Emit loading state first
                assertEquals(SearchResultsScreenViewState.Loading, awaitItem())

                // Emit successful state second
                val expectedViewState =
                    SearchResultsScreenViewState.Success(
                        name = "Tandarts",
                        city = "Roermond",
                        results = listOf(TEST_HEALTH_CARE_PROVIDER),
                    )
                assertEquals(expectedViewState, awaitItem())
            }
        }

    @Test
    fun `Given search results call failed, When getting search results, Then emit correct view state`() =
        runTest {
            // Given
            val error = IllegalStateException("Something went wrong")
            val viewModel =
                SearchResultsScreenViewModel(
                    savedStateHandle = savedStateHandle,
                    appInfo = appInfo,
                    healthCareProviderRepository = healthCareProviderRepository,
                )
            healthCareProviderRepository.setSearchResultsError(error)

            viewModel.viewState.test {
                // When
                viewModel.getSearchResults()

                // Emit loading state first
                assertEquals(SearchResultsScreenViewState.Loading, awaitItem())

                // Emit error state second
                val expectedViewState =
                    SearchResultsScreenViewState.Error(
                        isProductionBuild = true,
                        error = error,
                    )
                assertEquals(expectedViewState, awaitItem())
            }
        }

    @Test
    fun `Given viewmodel, When saving health care provider, Then navigate`() =
        runTest {
            // Given
            val viewModel =
                SearchResultsScreenViewModel(
                    savedStateHandle = savedStateHandle,
                    appInfo = appInfo,
                    healthCareProviderRepository = healthCareProviderRepository,
                )

            viewModel.navigation.test {
                // When
                viewModel.addHealthCareProvider(TEST_HEALTH_CARE_PROVIDER)

                // Then
                assertEquals(LocalisationNavigationScreen.StoredHealthCareProviders, awaitItem())
            }
        }
}
