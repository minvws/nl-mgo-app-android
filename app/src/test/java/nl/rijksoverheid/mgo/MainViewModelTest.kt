package nl.rijksoverheid.mgo

import app.cash.turbine.test
import io.mockk.mockk
import nl.rijksoverheid.mgo.data.config.ConfigState
import nl.rijksoverheid.mgo.data.config.TestConfigRepository
import nl.rijksoverheid.mgo.data.onboarding.TestHasSeenOnboarding
import nl.rijksoverheid.mgo.data.pincode.TestHasSeenPinCode
import nl.rijksoverheid.mgo.devicerooted.ShowDeviceRootedDialog
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigationScreen
import nl.rijksoverheid.mgo.navigation.onboarding.OnboardingNavigationScreen
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeNavigationScreen
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class MainViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `Given onboarding and pin code seen, When calling getStartDestination, Then return correct navigation`() {
        // Given
        val configRepository = TestConfigRepository()
        val hasSeenOnboarding = TestHasSeenOnboarding()
        val hasSeenPinCode = TestHasSeenPinCode()
        hasSeenOnboarding.set(true)
        hasSeenPinCode.set(true)
        val viewModel =
            MainViewModel(
                hasSeenOnboarding = hasSeenOnboarding,
                hasSeenPinCode = hasSeenPinCode,
                configRepository = configRepository,
                showDeviceRootedDialog = mockk<ShowDeviceRootedDialog>(),
            )

        // When
        val startDestination = viewModel.getStartDestination()

        // Then
        assertEquals(DashboardNavigationScreen.Start.getNavigationRoute(), startDestination)
    }

    @Test
    fun `Given onboarding seen, When calling getStartDestination, Then return correct navigation`() {
        // Given
        val configRepository = TestConfigRepository()
        val hasSeenOnboarding = TestHasSeenOnboarding()
        val hasSeenPinCode = TestHasSeenPinCode()
        hasSeenOnboarding.set(true)
        val viewModel =
            MainViewModel(
                hasSeenOnboarding = hasSeenOnboarding,
                hasSeenPinCode = hasSeenPinCode,
                configRepository = configRepository,
                showDeviceRootedDialog = mockk<ShowDeviceRootedDialog>(),
            )

        // When
        val startDestination = viewModel.getStartDestination()

        // Then
        assertEquals(PinCodeNavigationScreen.Start.getNavigationRoute(), startDestination)
    }

    @Test
    fun `Given nothing seen, When calling getStartDestination, Then return correct navigation`() {
        // Given
        val configRepository = TestConfigRepository()
        val hasSeenOnboarding = TestHasSeenOnboarding()
        val hasSeenPinCode = TestHasSeenPinCode()
        val viewModel =
            MainViewModel(
                hasSeenOnboarding = hasSeenOnboarding,
                hasSeenPinCode = hasSeenPinCode,
                configRepository = configRepository,
                showDeviceRootedDialog = mockk<ShowDeviceRootedDialog>(),
            )

        // When
        val startDestination = viewModel.getStartDestination()

        // Then
        assertEquals(OnboardingNavigationScreen.Start.getNavigationRoute(), startDestination)
    }

    @Test
    fun `Given initial config, When calling refresh with new config, Then config state flow is updated`() =
        runTest {
            // Given
            val configRepository = TestConfigRepository()
            val hasSeenOnboarding = TestHasSeenOnboarding()
            val hasSeenPinCode = TestHasSeenPinCode()
            val viewModel =
                MainViewModel(
                    hasSeenOnboarding = hasSeenOnboarding,
                    hasSeenPinCode = hasSeenPinCode,
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
