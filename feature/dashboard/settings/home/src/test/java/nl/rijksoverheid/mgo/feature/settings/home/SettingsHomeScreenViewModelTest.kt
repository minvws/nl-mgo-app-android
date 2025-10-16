package nl.rijksoverheid.mgo.feature.settings.home

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.theme.theme.AppTheme
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.pincode.biometric.TestDeviceHasBiometric
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class SettingsHomeScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val keyValueStore = TestKeyValueStore()
  private val secureKeyValueStore = TestKeyValueStore()
  private val organizationRepository = OrganizationRepository(okHttpClient = OkHttpClient(), baseUrl = "", mgoByteArrayStorage = MemoryMgoByteArrayStorage())

  @Before
  fun setup() =
    runTest {
      organizationRepository.deleteAll()
    }

  @Test
  fun testViewState() =
    runTest {
      // Given: View model
      val viewModel =
        SettingsHomeScreenViewModel(
          keyValueStore = keyValueStore,
          secureKeyValueStore = secureKeyValueStore,
          deviceHasBiometric = TestDeviceHasBiometric(true),
          isDebug = true,
          organizationRepository = organizationRepository,
        )

      // Then: App theme is system and device has biometric is true
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertEquals(AppTheme.SYSTEM, viewState.appTheme)
        assertEquals(true, viewState.deviceHasBiometric)
      }
    }

  @Test
  fun testResetApp() =
    runTest {
      // Given: Saved preferences and files
      secureKeyValueStore.setString(KEY_PIN_CODE, "123")
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: View model
      val viewModel =
        SettingsHomeScreenViewModel(
          keyValueStore = keyValueStore,
          secureKeyValueStore = secureKeyValueStore,
          deviceHasBiometric = TestDeviceHasBiometric(true),
          isDebug = true,
          organizationRepository = organizationRepository,
        )

      // When: Calling resetApp
      viewModel.resetApp()

      // Then: Saved preferences and files are deleted
      assertNull(secureKeyValueStore.getString(KEY_PIN_CODE))
      assertEquals(0, organizationRepository.get().size)
    }
}
