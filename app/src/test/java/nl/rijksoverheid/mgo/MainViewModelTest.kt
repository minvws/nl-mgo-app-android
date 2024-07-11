package nl.rijksoverheid.mgo

import app.cash.turbine.test
import io.mockk.mockk
import nl.rijksoverheid.mgo.data.config.ConfigState
import nl.rijksoverheid.mgo.data.config.TestConfigRepository
import nl.rijksoverheid.mgo.data.onboarding.TestHasSeenOnboarding
import nl.rijksoverheid.mgo.devicerooted.ShowDeviceRootedDialog
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class MainViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Given onboarding seen, When calling hasSeenOnboarding, Then return true`() {
        // Given
        val configRepository = TestConfigRepository()
        val hasSeenOnboarding = TestHasSeenOnboarding()
        hasSeenOnboarding.set(true)
        val viewModel =
            MainViewModel(
                hasSeenOnboarding = hasSeenOnboarding,
                configRepository = configRepository,
                showDeviceRootedDialog = mockk<ShowDeviceRootedDialog>(),
            )

        // When
        val hasSeen = viewModel.hasSeenOnboarding()

        // Then
        assertEquals(true, hasSeen)
    }

    @Test
    fun `Given initial config, When calling refresh with new config, Then config state flow is updated`() =
        runTest {
            // Given
            val configRepository = TestConfigRepository()
            val hasSeenOnboarding = TestHasSeenOnboarding()
            val viewModel =
                MainViewModel(
                    hasSeenOnboarding = hasSeenOnboarding,
                    configRepository = configRepository,
                    showDeviceRootedDialog = mockk<ShowDeviceRootedDialog>(),
                )

            // When
            configRepository.setConfigState(ConfigState.UpdateRequired)
            viewModel.refreshConfig()

            // Then
            viewModel.configStateFlow.test {
                assertEquals(ConfigState.UpdateRequired, awaitItem())
            }
        }
}
