package nl.rijksoverheid.mgo.feature.settings.advanced

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_FLAG_SECURE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_SKIP_PIN
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

internal class SettingsAdvancedScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Test
  fun testViewState() =
    runTest {
      // Given: Key value store with some toggles
      val keyValueStore = TestKeyValueStore()
      keyValueStore.setBoolean(KEY_AUTOMATIC_LOCALISATION, true)
      keyValueStore.setBoolean(KEY_FLAG_SECURE, false)
      keyValueStore.setBoolean(KEY_SKIP_PIN, true)

      // Given: View model
      val viewModel = SettingsAdvancedScreenViewModel(ioDispatcher = mainDispatcherRule.testDispatcher, keyValueStore = keyValueStore)

      // Then: View state matches toggles from key value store
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertTrue(viewState.automaticLocalisation)
        assertFalse(viewState.flagSecure)
        assertTrue(viewState.skipPinCode)
      }
    }

  @Test
  fun testSetToggle() =
    runTest {
      // Given: Key value store with automatic localisation set to false
      val keyValueStore = TestKeyValueStore()
      keyValueStore.setBoolean(KEY_AUTOMATIC_LOCALISATION, false)

      // Given: View model
      val viewModel = SettingsAdvancedScreenViewModel(ioDispatcher = mainDispatcherRule.testDispatcher, keyValueStore = keyValueStore)

      // When: Enabling automatic localisation
      viewModel.setToggle(KEY_AUTOMATIC_LOCALISATION, true)

      // Then: View state is updated
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertTrue(viewState.automaticLocalisation)
      }
    }
}
