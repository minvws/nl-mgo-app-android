package nl.rijksoverheid.mgo.feature.localisation.search

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import nl.rijksoverheid.mgo.feature.localisation.navigation.LocalisationNavigationScreen
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

internal class SearchScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Given ViewModel, When no name and city have been entered, Then emit error`() =
        runTest {
            // Given
            val viewModel = SearchScreenViewModel()

            // When
            viewModel.validate()

            // Then
            viewModel.viewState.test {
                val expectedViewState =
                    SearchScreenViewState(
                        name = "",
                        nameError = CopyR.string.localisation_search_name_error,
                        city = "",
                        cityError = CopyR.string.localisation_search_city_error,
                    )
                assertEquals(expectedViewState, awaitItem())
            }
        }

    @Test
    fun `Given ViewModel, When no name has been entered, Then emit error`() =
        runTest {
            // Given
            val viewModel = SearchScreenViewModel()

            // When
            viewModel.setCity("Roermond")
            viewModel.validate()

            // Then
            viewModel.viewState.test {
                val expectedViewState =
                    SearchScreenViewState(
                        name = "",
                        nameError = CopyR.string.localisation_search_name_error,
                        city = "Roermond",
                        cityError = null,
                    )
                assertEquals(expectedViewState, awaitItem())
            }
        }

    @Test
    fun `Given ViewModel, When no city has been entered, Then emit error`() =
        runTest {
            // Given
            val viewModel = SearchScreenViewModel()

            // When
            viewModel.setName("Tandarts")
            viewModel.validate()

            // Then
            viewModel.viewState.test {
                val expectedViewState =
                    SearchScreenViewState(
                        name = "Tandarts",
                        nameError = null,
                        city = "",
                        cityError = CopyR.string.localisation_search_city_error,
                    )
                assertEquals(expectedViewState, awaitItem())
            }
        }

    @Test
    fun `Given ViewModel, When name and city have been entered, Then navigate`() =
        runTest {
            // Given
            val viewModel = SearchScreenViewModel()

            // When
            viewModel.setName("Tandarts")
            viewModel.setCity("Roermond")

            turbineScope {
                val viewStateFlow = viewModel.viewState.testIn(backgroundScope)
                val navigationFlow = viewModel.navigation.testIn(backgroundScope)
                viewModel.validate()

                // Then
                val expectedViewState =
                    SearchScreenViewState(
                        name = "Tandarts",
                        nameError = null,
                        city = "Roermond",
                        cityError = null,
                    )
                assertEquals(expectedViewState, viewStateFlow.awaitItem())

                val expectedRoute = LocalisationNavigationScreen.SearchResults.setName("Tandarts").setCity("Roermond")
                assertEquals(expectedRoute, navigationFlow.awaitItem())
            }
        }
}
