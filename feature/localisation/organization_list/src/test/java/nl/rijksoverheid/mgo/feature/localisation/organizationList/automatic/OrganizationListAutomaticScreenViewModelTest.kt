package nl.rijksoverheid.mgo.feature.localisation.organizationList.automatic

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

internal class OrganizationListAutomaticScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule()

  private val organizationRepository = TestOrganizationRepository()
  private val viewModel by lazy {
    OrganizationListAutomaticScreenViewModel(organizationRepository)
  }

  @After
  fun cleanUp() {
    organizationRepository.resetSearchResults()
  }

  @Test
  fun testGetSearchResultsSuccess() =
    runTest {
      // Given: search results return organizations
      setSearchResultsSuccess(listOf(TEST_MGO_ORGANIZATION))

      // When: calling getSearchResults
      viewModel.getSearchResults()

      // Then: view state is updated with results
      val expectedViewState =
        OrganizationListAutomaticScreenViewState(
          loading = false,
          results = listOf(TEST_MGO_ORGANIZATION),
          error = null,
        )
      assertViewState(expectedViewState)
    }

  @Test
  fun testGetSearchResultsFailed() =
    runTest {
      // Given: search results return error
      val error = IllegalStateException("Something went wrong")
      setSearchResultsFailed(error)

      // When: calling getSearchResults
      viewModel.getSearchResults()

      // Then: view state is updated without results and with error
      val expectedViewState =
        OrganizationListAutomaticScreenViewState(
          loading = false,
          results = listOf(),
          error = error,
        )
      assertViewState(expectedViewState)
    }

  @Test
  fun testUpdateOrganization() =
    runTest {
      // Given: view state has organization that is not added
      setSearchResultsSuccess(listOf(TEST_MGO_ORGANIZATION.copy(added = false)))
      viewModel.getSearchResults()

      // When: calling updateOrganization for that organization with added set to true
      viewModel.updateOrganization(organization = TEST_MGO_ORGANIZATION, added = true)

      // Then: view state is updated and has added organization
      val expectedViewState =
        OrganizationListAutomaticScreenViewState(
          loading = false,
          results = listOf(TEST_MGO_ORGANIZATION.copy(added = true)),
          error = null,
        )
      assertViewState(expectedViewState)
    }

  @Test
  fun testUpdateOrganizations() =
    runTest {
      // Given: Some organizations
      val organization1 = TEST_MGO_ORGANIZATION.copy(id = "1")
      val organization2 = TEST_MGO_ORGANIZATION.copy(id = "2")
      val organization3 = TEST_MGO_ORGANIZATION.copy(id = "3")

      // Given: Stored organizations
      organizationRepository.setStoredProviders(listOf(organization1, organization2))

      // Given: fetched organizations
      setSearchResultsSuccess(listOf(organization1, organization2, organization3))

      // Given: user unchecks organization1
      viewModel.updateOrganization(organization = organization1, added = false)

      // Given: user checks organization3
      viewModel.updateOrganization(organization = organization3, added = true)

      // When calling addCheckedOrganizations
      viewModel.updateOrganizations()

      // Then: organization1 is removed
      val storedOrganizations = organizationRepository.get()
      assertTrue(!storedOrganizations.contains(organization1))

      // Then: organization3 is added
      assertEquals(organization3.copy(added = true), storedOrganizations.last())
    }

  private suspend fun setSearchResultsSuccess(organizations: List<MgoOrganization>) {
    organizationRepository.setSearchResults(organizations)
  }

  private fun setSearchResultsFailed(error: Throwable) {
    organizationRepository.setSearchResultsError(error)
  }

  private suspend fun assertViewState(viewState: OrganizationListAutomaticScreenViewState) {
    viewModel.viewState.test {
      assertEquals(viewState, awaitItem())
    }
  }
}
