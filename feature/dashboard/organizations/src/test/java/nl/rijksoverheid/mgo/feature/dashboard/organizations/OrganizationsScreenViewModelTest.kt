package nl.rijksoverheid.mgo.feature.dashboard.organizations

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class OrganizationsScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val organizationRepository = OrganizationRepository(okHttpClient = OkHttpClient(), baseUrl = "", mgoByteArrayStorage = MemoryMgoByteArrayStorage())
  private val keyValueStore = TestKeyValueStore()

  @Before
  fun setUp() =
    runTest {
      keyValueStore.setBoolean(KEY_AUTOMATIC_LOCALISATION, false)
      organizationRepository.deleteAll()
    }

  @Test
  fun `Given stored organizations, When collecting on view state, Then emit view state with organizations`() =
    runTest {
      // Given
      organizationRepository.save(TEST_MGO_ORGANIZATION)

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
