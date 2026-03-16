package nl.rijksoverheid.mgo.feature.localisation.manual

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ManualLocalisationScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val organizationRepository = mockk<OrganizationRepository>()
  private val viewModel =
    ManualLocalisationScreenViewModel(
      organizationRepository = organizationRepository,
      ioDispatcher = mainDispatcherRule.testDispatcher,
    )

  @Before
  fun setup() {
    coEvery { organizationRepository.save(any()) } answers { }
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun testSearch() =
    runTest {
      // Given: Return organization when searching
      coEvery { organizationRepository.search(any(), any()) } coAnswers { flowOf(listOf(TEST_MGO_ORGANIZATION)) }

      // When: Searching
      viewModel.search("UMC Groningen")

      // Then: organization exists in view state
      advanceUntilIdle()
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertEquals(1, viewState.organizations?.size)
        assertEquals(
          "Tandarts Tandje Erbij",
          viewState.organizations?.first()?.name,
        )
        assertFalse(viewState.error)
      }
    }

  @Test
  fun testSearchError() =
    runTest {
      // Given: Searching for organization errors
      coEvery { organizationRepository.search(any(), any()) } coAnswers { error("Something went wrong") }

      // When: Searching
      viewModel.search("UMC Groningen")

      // Then: organization exists in view state
      advanceUntilIdle()
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertNull(viewState.organizations)
        assertTrue(viewState.error)
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
