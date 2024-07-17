package nl.rijksoverheid.mgo.feature.localisation.organizationSearch

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.TestEnvironmentRepository
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.lang.IllegalStateException
import kotlinx.coroutines.test.runTest

internal class OrganizationSearchScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val organizationRepository =
        TestOrganizationRepository()
    private val environmentRepository = TestEnvironmentRepository().also { it.setEnvironment(Environment.Prod(1)) }

    @After
    fun cleanUp() {
        organizationRepository.resetSearchResults()
    }

    @Test
    fun `Given search results call success, When getting search results, Then emit correct view state`() =
        runTest {
            // Given
            val viewModel =
                OrganizationSearchScreenViewModel(
                    environmentRepository = environmentRepository,
                    organizationRepository = organizationRepository,
                )
            organizationRepository.setSearchResults(listOf(TEST_MGO_ORGANIZATION))

            viewModel.viewState.test {
                // When
                viewModel.getSearchResults(name = "Tandarts", city = "Roermond")

                // Emit loading state first
                assertEquals(OrganizationSearchScreenViewState.Loading, awaitItem())

                // Emit successful state second
                val expectedViewState =
                    OrganizationSearchScreenViewState.Success(
                        name = "Tandarts",
                        city = "Roermond",
                        results = listOf(TEST_MGO_ORGANIZATION),
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
                OrganizationSearchScreenViewModel(
                    environmentRepository = environmentRepository,
                    organizationRepository = organizationRepository,
                )
            organizationRepository.setSearchResultsError(error)

            viewModel.viewState.test {
                // When
                viewModel.getSearchResults(name = "Tandarts", city = "Roermond")

                // Emit loading state first
                assertEquals(OrganizationSearchScreenViewState.Loading, awaitItem())

                // Emit error state second
                val expectedViewState =
                    OrganizationSearchScreenViewState.Error(
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
                OrganizationSearchScreenViewModel(
                    environmentRepository = environmentRepository,
                    organizationRepository = organizationRepository,
                )

            viewModel.navigation.test {
                // When
                viewModel.addOrganization(TEST_MGO_ORGANIZATION)

                // Then
                assertEquals(Unit, awaitItem())
            }
        }
}
