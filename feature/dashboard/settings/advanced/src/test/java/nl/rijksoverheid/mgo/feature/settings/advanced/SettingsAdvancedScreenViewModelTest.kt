package nl.rijksoverheid.mgo.feature.settings.advanced

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.environment.featureToggle.FEATURE_TOGGLE_SKIP_DIGID_LOGIN
import nl.rijksoverheid.mgo.framework.environment.featureToggle.FeatureToggleRepository
import nl.rijksoverheid.mgo.framework.storage.keyvalue.MemoryMgoKeyValueStorage
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsAdvancedScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()
  private val keyValueStore = MemoryMgoKeyValueStorage()
  private val featureToggleRepository = FeatureToggleRepository(keyValueStore)

  private lateinit var viewModel: SettingsAdvancedScreenViewModel

  @Before
  fun setup() {
    viewModel = SettingsAdvancedScreenViewModel(featureToggleRepository = featureToggleRepository, ioDispatcher = mainDispatcherRule.testDispatcher)
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun testSetToggle() =
    runTest {
      viewModel.viewState.test {
        // Given: Toggle is false
        val firstViewState = awaitItem()
        assertFalse(firstViewState.featureToggles.first { it.toggle == FEATURE_TOGGLE_SKIP_DIGID_LOGIN }.value as Boolean)

        // When: Set toggle is called
        advanceUntilIdle()
        viewModel.setToggle(toggle = FEATURE_TOGGLE_SKIP_DIGID_LOGIN, value = true)

        // Then: Toggle is true
        val secondViewState = awaitItem()
        assertTrue(secondViewState.featureToggles.first { it.toggle == FEATURE_TOGGLE_SKIP_DIGID_LOGIN }.value as Boolean)
      }
    }
}
