package nl.rijksoverheid.mgo.feature.dashboard.organizations

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class OrganizationsScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule()

  private val organizationRepository = TestOrganizationRepository()
  private val keyValueStore = TestKeyValueStore()

  @Before
  fun setUp() =
    runTest {
      keyValueStore.setBoolean(KEY_AUTOMATIC_LOCALISATION, false)
    }

  @Test
  fun `Given stored organizations, When collecting on view state, Then emit view state with organizations`() =
    runTest {
      // Given
      organizationRepository.setStoredProviders(listOf(TEST_MGO_ORGANIZATION))

      // When
      val viewModel =
        OrganizationsViewModel(
          organizationRepository = organizationRepository,
          keyValueStore = keyValueStore,
        )
      viewModel.viewState.test {
        // Then
        assertEquals(listOf(TEST_MGO_ORGANIZATION), awaitItem().organizations)
      }
    }
}
