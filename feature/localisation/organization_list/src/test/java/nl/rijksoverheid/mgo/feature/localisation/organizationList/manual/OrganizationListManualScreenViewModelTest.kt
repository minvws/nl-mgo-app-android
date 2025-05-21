package nl.rijksoverheid.mgo.feature.localisation.organizationList.manual

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

internal class OrganizationListManualScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule()

  private val organizationRepository = TestOrganizationRepository()

  @After
  fun cleanUp() {
    organizationRepository.resetSearchResults()
  }

  @Test
  fun `Given search results call success, When getting search results, Then emit correct view state`() =
    runTest {
      // Given
      organizationRepository.setSearchResults(listOf(TEST_MGO_ORGANIZATION))
      val viewModel =
        OrganizationListManualScreenViewModel(
          name = "Tandarts",
          city = "Roermond",
          organizationRepository = organizationRepository,
        )

      // When
      viewModel.viewState.test {
        val expectedViewState =
          OrganizationListManualScreenViewState(
            name = "Tandarts",
            city = "Roermond",
            results = listOf(TEST_MGO_ORGANIZATION),
            loading = false,
            error = null,
          )

        // Then
        assertEquals(expectedViewState, awaitItem())
      }
    }

  @Test
  fun `Given search results call failed, When getting search results, Then emit correct view state`() =
    runTest {
      // Given
      val error = IllegalStateException("Something went wrong")
      organizationRepository.setSearchResultsError(error)
      val viewModel =
        OrganizationListManualScreenViewModel(
          name = "Tandarts",
          city = "Roermond",
          organizationRepository = organizationRepository,
        )

      // When
      viewModel.viewState.test {
        val expectedViewState =
          OrganizationListManualScreenViewState(
            name = "Tandarts",
            city = "Roermond",
            results = listOf(),
            loading = false,
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
        OrganizationListManualScreenViewModel(
          name = "Tandarts",
          city = "Roermond",
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
