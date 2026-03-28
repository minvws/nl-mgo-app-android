package nl.rijksoverheid.mgo.feature.localisation.manual

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import nl.rijksoverheid.mgo.data.organization.TestOrganizationApiClient
import nl.rijksoverheid.mgo.data.organization.createOrganizationRepositoryForJvm
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ManualLocalisationScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val apiClient = TestOrganizationApiClient()
  private lateinit var organizationRepository: OrganizationRepository
  private lateinit var viewModel: ManualLocalisationScreenViewModel

  @Before
  fun setup() {
    organizationRepository = createOrganizationRepositoryForJvm(apiClient)
    viewModel =
      ManualLocalisationScreenViewModel(
        organizationRepository = organizationRepository,
        ioDispatcher = mainDispatcherRule.testDispatcher,
      )
  }

  @Test
  fun testSearchShortQuery() =
    runTest {
      // Given: User searches one query
      val query = "a"

      // When: Calling search
      viewModel.search(query)

      // Then: View state is updated
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertFalse(viewState.loading)
        assertFalse(viewState.error)
        assertNull(viewState.organizations)
      }
    }

  @Test
  fun testSearchSyncSuccess() =
    runTest {
      // Given: User searches one query
      val query = "abc"

      // When: Calling search
      viewModel.search(query)
      advanceUntilIdle()

      // Then: View state is updated
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertFalse(viewState.loading)
        assertFalse(viewState.error)
        assertNotNull(viewState.organizations)
      }
    }

  @Test
  fun testSearchSyncFailed() =
    runTest {
      // Given sync fails
      apiClient.setOrganizationsResult(Result.failure(IllegalStateException("Something went wrong")))
      apiClient.setEndpointsResult(Result.failure(IllegalStateException("Something went wrong")))

      // Given: User searches one query
      val query = "abc"

      // When: Calling search
      viewModel.search(query)
      advanceUntilIdle()

      // Then: View state is updated
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertFalse(viewState.loading)
        assertTrue(viewState.error)
        assertNull(viewState.organizations)
      }
    }

  @Test
  fun testAdd() =
    runTest {
      viewModel.navigateToDashboard.test {
        viewModel.add(TEST_MGO_ORGANIZATION)
        assertEquals(awaitItem(), Unit)
      }
    }
}
