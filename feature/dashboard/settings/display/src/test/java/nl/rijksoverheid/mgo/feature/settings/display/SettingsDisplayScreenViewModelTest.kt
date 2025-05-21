package nl.rijksoverheid.mgo.feature.settings.display

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.theme.theme.AppTheme
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

internal class SettingsDisplayScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Test
  fun testSetAppTheme() =
    runTest {
      // Given: View model
      val viewModel = SettingsDisplayScreenViewModel(keyValueStore = TestKeyValueStore())

      // When: Calling setAppTheme
      viewModel.setTheme(AppTheme.DARK)

      // Then: App theme is set to dark

      viewModel.appTheme.test {
        assertEquals(AppTheme.DARK, awaitItem())
      }
    }
}
