package nl.rijksoverheid.mgo

import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import nl.rijksoverheid.mgo.data.onboarding.TestHasSeenOnboarding
import nl.rijksoverheid.mgo.data.pincode.TestHasPinCode
import nl.rijksoverheid.mgo.devicerooted.ShowDeviceRootedDialog
import nl.rijksoverheid.mgo.framework.featuretoggle.FeatureToggleId
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.lock.TestAppLocked
import nl.rijksoverheid.mgo.lock.TestSaveClosedAppTimestamp
import nl.rijksoverheid.mgo.navigation.onboarding.OnboardingNavigation
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeCreateNavigation
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeLoginNavigation
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest

internal class MainViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val featureToggleRepository = mockk<FeatureToggleRepository>()

    @Before
    fun setup() {
        every { featureToggleRepository.observe(FeatureToggleId.FlagSecure) } answers { flow { emit(false) } }
        every { featureToggleRepository.get(FeatureToggleId.SkipPin) } answers { false }
    }

    @Test
    fun `Given has pin code, When calling getStartDestination, Then return correct navigation`() {
        // Given
        val hasSeenOnboarding = TestHasSeenOnboarding()
        val hasPinCode = TestHasPinCode()
        hasPinCode.set(true)
        val viewModel =
            MainViewModel(
                hasSeenOnboarding = hasSeenOnboarding,
                hasPinCode = hasPinCode,
                showDeviceRootedDialog = mockk<ShowDeviceRootedDialog>(),
                saveClosedAppTimestamp = TestSaveClosedAppTimestamp(),
                appLocked = TestAppLocked(false),
                featureToggleRepository = featureToggleRepository,
            )

        // When
        val startDestination = viewModel.getStartDestination()

        // Then
        assertEquals(PinCodeLoginNavigation.Root, startDestination)
    }

    @Test
    fun `Given onboarding seen, When calling getStartDestination, Then return correct navigation`() {
        // Given
        val hasSeenOnboarding = TestHasSeenOnboarding()
        val hasPinCode = TestHasPinCode()
        hasSeenOnboarding.set(true)
        val viewModel =
            MainViewModel(
                hasSeenOnboarding = hasSeenOnboarding,
                hasPinCode = hasPinCode,
                showDeviceRootedDialog = mockk<ShowDeviceRootedDialog>(),
                saveClosedAppTimestamp = TestSaveClosedAppTimestamp(),
                appLocked = TestAppLocked(false),
                featureToggleRepository = featureToggleRepository,
            )

        // When
        val startDestination = viewModel.getStartDestination()

        // Then
        assertEquals(PinCodeCreateNavigation.Root, startDestination)
    }

    @Test
    fun `Given onboarding not seen, When calling getStartDestination, Then return correct navigation`() {
        // Given
        val hasSeenOnboarding = TestHasSeenOnboarding()
        val hasPinCode = TestHasPinCode()
        hasSeenOnboarding.set(false)
        val viewModel =
            MainViewModel(
                hasSeenOnboarding = hasSeenOnboarding,
                hasPinCode = hasPinCode,
                showDeviceRootedDialog = mockk<ShowDeviceRootedDialog>(),
                saveClosedAppTimestamp = TestSaveClosedAppTimestamp(),
                appLocked = TestAppLocked(false),
                featureToggleRepository = featureToggleRepository,
            )

        // When
        val startDestination = viewModel.getStartDestination()

        // Then
        assertEquals(OnboardingNavigation.Root, startDestination)
    }

    @Test
    fun `Given app is locked, When calling checkAppLock, Then navigate to login dialog`() =
        runTest {
            // Given
            val hasSeenOnboarding = TestHasSeenOnboarding()
            val hasPinCode = TestHasPinCode()
            val viewModel =
                MainViewModel(
                    hasSeenOnboarding = hasSeenOnboarding,
                    hasPinCode = hasPinCode,
                    showDeviceRootedDialog = mockk<ShowDeviceRootedDialog>(),
                    saveClosedAppTimestamp = TestSaveClosedAppTimestamp(),
                    appLocked = TestAppLocked(true),
                    featureToggleRepository = featureToggleRepository,
                )

            viewModel.navigateDialog.test {
                // When
                viewModel.checkAppLock()

                // Then
                assertEquals(PinCodeLoginNavigation.LoginDialog, awaitItem())
            }
        }

    @Test
    fun `Given app is not locked, When calling checkAppLock, Then do not navigate to login dialog`() =
        runTest {
            // Given
            val hasSeenOnboarding = TestHasSeenOnboarding()
            val hasPinCode = TestHasPinCode()
            val viewModel =
                MainViewModel(
                    hasSeenOnboarding = hasSeenOnboarding,
                    hasPinCode = hasPinCode,
                    showDeviceRootedDialog = mockk<ShowDeviceRootedDialog>(),
                    saveClosedAppTimestamp = TestSaveClosedAppTimestamp(),
                    appLocked = TestAppLocked(false),
                    featureToggleRepository = featureToggleRepository,
                )

            viewModel.navigateDialog.test {
                // When
                viewModel.checkAppLock()

                // Then
                expectNoEvents()
            }
        }

    @Test
    fun `Given timestamp, When calling saveClosedAppTimestamp, Then save timestamp`() =
        runTest {
            // Given
            val hasSeenOnboarding = TestHasSeenOnboarding()
            val hasPinCode = TestHasPinCode()
            val saveClosedAppTimestamp = TestSaveClosedAppTimestamp()
            val viewModel =
                MainViewModel(
                    hasSeenOnboarding = hasSeenOnboarding,
                    hasPinCode = hasPinCode,
                    showDeviceRootedDialog = mockk<ShowDeviceRootedDialog>(),
                    saveClosedAppTimestamp = saveClosedAppTimestamp,
                    appLocked = TestAppLocked(true),
                    featureToggleRepository = featureToggleRepository,
                )

            // When
            viewModel.saveClosedAppTimestamp()

            // Then
            assertEquals(true, saveClosedAppTimestamp.saved)
        }
}
