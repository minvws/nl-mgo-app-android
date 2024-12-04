package nl.rijksoverheid.mgo.feature.localisation.organizationList.automatic

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class OrganizationListAutomaticScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

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
    fun testAddCheckedOrganizations() =
        runTest {
            // Given: two organizations that are added and one organization that is not added
            val organization1 = TEST_MGO_ORGANIZATION.copy(id = "1", added = true)
            val organization2 = TEST_MGO_ORGANIZATION.copy(id = "2", added = false)
            val organization3 = TEST_MGO_ORGANIZATION.copy(id = "3", added = true)
            setSearchResultsSuccess(listOf(organization1, organization2, organization3))
            viewModel.getSearchResults()

            // When calling addCheckedOrganizations
            viewModel.addCheckedOrganizations()

            // Then: Stored organizations contain organization1 and organization3
            val storedOrganizations = organizationRepository.get()
            assertEquals(2, storedOrganizations.size)
            assertEquals(organization1, storedOrganizations[0])
            assertEquals(organization3, storedOrganizations[1])
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
