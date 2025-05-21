package nl.rijksoverheid.mgo.feature.settings.security

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_LOGIN_WITH_BIOMETRIC_ENABLED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

internal class SettingsSecurityScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Test
  fun testViewState() =
    runTest {
      // Given: Biometric login is enabled
      val keyValueStore = TestKeyValueStore()
      keyValueStore.setBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED, true)

      // Given: View model
      val viewModel = SettingsSecurityScreenViewModel(ioDispatcher = mainDispatcherRule.testDispatcher, keyValueStore = keyValueStore)

      // Then: Biometric login is enabled
      viewModel.biometricEnabled.test {
        assertTrue(awaitItem())
      }
    }

  @Test
  fun testSetBiometricEnabled() =
    runTest {
      // Given: Biometric login is disabled
      val keyValueStore = TestKeyValueStore()
      keyValueStore.setBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED, false)

      // Given: View model
      val viewModel = SettingsSecurityScreenViewModel(ioDispatcher = mainDispatcherRule.testDispatcher, keyValueStore = keyValueStore)

      // When: Enabling biometric login
      viewModel.setBiometricEnabled(true)

      // Then: Biometric login is enabled
      viewModel.biometricEnabled.test {
        assertTrue(awaitItem())
      }
    }
}
