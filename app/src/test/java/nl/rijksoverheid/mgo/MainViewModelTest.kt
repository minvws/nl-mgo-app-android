package nl.rijksoverheid.mgo

import app.cash.turbine.test
import io.mockk.mockk
import nl.rijksoverheid.mgo.data.config.ConfigState
import nl.rijksoverheid.mgo.data.config.TestConfigRepository
import nl.rijksoverheid.mgo.data.onboarding.TestHasSeenOnboarding
import nl.rijksoverheid.mgo.data.pincode.TestHasPinCode
import nl.rijksoverheid.mgo.devicerooted.ShowDeviceRootedDialog
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.lock.TestAppLocked
import nl.rijksoverheid.mgo.lock.TestSaveClosedAppTimestamp
import nl.rijksoverheid.mgo.navigation.onboarding.OnboardingNavigation
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeCreateNavigation
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class MainViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Given onboarding seen, When calling getStartDestination, Then return correct navigation`() {
        // Given
        val configRepository = TestConfigRepository()
        val hasSeenOnboarding = TestHasSeenOnboarding()
        val hasPinCode = TestHasPinCode()
        hasSeenOnboarding.set(true)
        val viewModel =
            MainViewModel(
                hasSeenOnboarding = hasSeenOnboarding,
                hasPinCode = hasPinCode,
                configRepository = configRepository,
                showDeviceRootedDialog = mockk<ShowDeviceRootedDialog>(),
                saveClosedAppTimestamp = TestSaveClosedAppTimestamp(),
                appLocked = TestAppLocked(false),
            )

        // When
        val startDestination = viewModel.getStartDestination()

        // Then
        assertEquals(PinCodeCreateNavigation.Root, startDestination)
    }

    @Test
    fun `Given onboarding not seen, When calling getStartDestination, Then return correct navigation`() {
        // Given
        val configRepository = TestConfigRepository()
        val hasSeenOnboarding = TestHasSeenOnboarding()
        val hasPinCode = TestHasPinCode()
        hasSeenOnboarding.set(false)
        val viewModel =
            MainViewModel(
                hasSeenOnboarding = hasSeenOnboarding,
                hasPinCode = hasPinCode,
                configRepository = configRepository,
                showDeviceRootedDialog = mockk<ShowDeviceRootedDialog>(),
                saveClosedAppTimestamp = TestSaveClosedAppTimestamp(),
                appLocked = TestAppLocked(false),
            )

        // When
        val startDestination = viewModel.getStartDestination()

        // Then
        assertEquals(OnboardingNavigation.Root, startDestination)
    }

    @Test
    fun `Given initial config, When calling refresh with new config, Then config state flow is updated`() =
        runTest {
            // Given
            val configRepository = TestConfigRepository()
            val hasSeenOnboarding = TestHasSeenOnboarding()
            val hasPinCode = TestHasPinCode()
            val viewModel =
                MainViewModel(
                    hasSeenOnboarding = hasSeenOnboarding,
                    hasPinCode = hasPinCode,
                    configRepository = configRepository,
                    showDeviceRootedDialog = mockk<ShowDeviceRootedDialog>(),
                    saveClosedAppTimestamp = TestSaveClosedAppTimestamp(),
                    appLocked = TestAppLocked(false),
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
