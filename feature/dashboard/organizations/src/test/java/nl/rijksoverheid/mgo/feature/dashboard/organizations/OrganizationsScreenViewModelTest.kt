package nl.rijksoverheid.mgo.feature.dashboard.organizations

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import nl.rijksoverheid.mgo.data.organization.createOrganizationRepositoryForJvm
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class OrganizationsScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private lateinit var organizationRepository: OrganizationRepository
  private val keyValueStore = TestKeyValueStore()

  @Before
  fun setUp() =
    runTest {
      organizationRepository = createOrganizationRepositoryForJvm()
      keyValueStore.setBoolean(KEY_AUTOMATIC_LOCALISATION, false)
    }

  @Test
  fun `Given stored organizations, When collecting on view state, Then emit view state with organizations`() =
    runTest {
      // Given
      organizationRepository.addAndSave(TEST_MGO_ORGANIZATION)

      // When
      val viewModel =
        OrganizationsViewModel(
          organizationRepository = organizationRepository,
          keyValueStore = keyValueStore,
          ioDispatcher = mainDispatcherRule.testDispatcher,
        )
      viewModel.viewState.test {
        // Then
        assertEquals(TEST_MGO_ORGANIZATION.id, awaitItem().organizations.first().id)
      }
    }
}
