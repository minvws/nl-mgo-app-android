package nl.rijksoverheid.mgo.feature.settings.home

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.theme.theme.AppTheme
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.pincode.biometric.TestDeviceHasBiometric
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_HAS_SEEN_ONBOARDING
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestCacheFileStore
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

internal class SettingsHomeScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val keyValueStore = TestKeyValueStore()
  private val secureKeyValueStore = TestKeyValueStore()
  private val cacheFileStore = TestCacheFileStore()
  private val organizationRepository = TestOrganizationRepository()

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
          cacheFileStore = cacheFileStore,
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
      keyValueStore.setBoolean(KEY_HAS_SEEN_ONBOARDING, true)
      secureKeyValueStore.setString(KEY_PIN_CODE, "123")
      cacheFileStore.saveFile("file1.json", "", "")
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: View model
      val viewModel =
        SettingsHomeScreenViewModel(
          keyValueStore = keyValueStore,
          secureKeyValueStore = secureKeyValueStore,
          deviceHasBiometric = TestDeviceHasBiometric(true),
          isDebug = true,
          cacheFileStore = cacheFileStore,
          organizationRepository = organizationRepository,
        )

      // When: Calling resetApp
      viewModel.resetApp()

      // Then: Saved preferences and files are deleted
      assertFalse(keyValueStore.getBoolean(KEY_HAS_SEEN_ONBOARDING))
      assertNull(secureKeyValueStore.getString(KEY_PIN_CODE))
      cacheFileStore.assertNoFilesSaved()
      organizationRepository.assertNoProviders()
    }
}
