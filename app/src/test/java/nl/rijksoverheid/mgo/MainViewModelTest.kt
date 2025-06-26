package nl.rijksoverheid.mgo

import app.cash.turbine.test
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.digid.TestIsDigidAuthenticated
import nl.rijksoverheid.mgo.data.onboarding.TestHasSeenOnboarding
import nl.rijksoverheid.mgo.data.pincode.TestHasPinCode
import nl.rijksoverheid.mgo.devicerooted.ShowDeviceRootedDialog
import nl.rijksoverheid.mgo.framework.featuretoggle.TestFeatureToggleRepository
import nl.rijksoverheid.mgo.framework.featuretoggle.flagSkipPinFeatureToggle
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.lifecycle.TestAppLifecycleRepository
import nl.rijksoverheid.mgo.lock.TestAppLocked
import nl.rijksoverheid.mgo.lock.TestSaveClosedAppTimestamp
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigation
import nl.rijksoverheid.mgo.navigation.digid.DigidNavigation
import nl.rijksoverheid.mgo.navigation.onboarding.OnboardingNavigation
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeCreateNavigation
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeLoginNavigation
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

internal class MainViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val featureToggleRepository = TestFeatureToggleRepository(listOf())
  private val keyValueStore = TestKeyValueStore()
  private val hasSeenOnboarding = TestHasSeenOnboarding()
  private val hasPinCode = TestHasPinCode()
  private val appLocked = TestAppLocked()
  private val isDigidAuthenticated = TestIsDigidAuthenticated()
  private val saveClosedAppTimestamp = TestSaveClosedAppTimestamp()
  private val viewModel by lazy {
    MainViewModel(
      hasSeenOnboarding = hasSeenOnboarding,
      hasPinCode = hasPinCode,
      showDeviceRootedDialog = mockk<ShowDeviceRootedDialog>(),
      saveClosedAppTimestamp = saveClosedAppTimestamp,
      appLocked = appLocked,
      featureToggleRepository = featureToggleRepository,
      keyValueStore = keyValueStore,
      isDigidAuthenticated = isDigidAuthenticated,
      appLifecycleRepository = TestAppLifecycleRepository(),
    )
  }

  @Test
  fun testStartDestinationOnboarding() {
    // Given: Onboarding not seen
    hasSeenOnboarding.set(false)

    // When: Getting start destination
    val startDestination = viewModel.getStartDestination()

    // Then: Start destination is onboarding
    assertEquals(OnboardingNavigation.Root, startDestination)
  }

  @Test
  fun testStartDestinationPinCodeCreate() {
    // Given: Onboarding seen
    hasSeenOnboarding.set(true)

    // Given: No pin code
    hasPinCode.set(false)

    // When: Getting start destination
    val startDestination = viewModel.getStartDestination()

    // Then: Start destination is create pin code
    assertEquals(PinCodeCreateNavigation.Root, startDestination)
  }

  @Test
  fun testStartDestinationDigid() {
    // Given: Onboarding seen
    hasSeenOnboarding.set(true)

    // Given: Has pin code
    hasPinCode.set(true)

    // Given: Not authenticated with DigiD
    isDigidAuthenticated.set(false)

    // When: Getting start destination
    val startDestination = viewModel.getStartDestination()

    // Then: Start destination is DigiD
    assertEquals(DigidNavigation.Root, startDestination)
  }

  @Test
  fun testStartDestinationPinCodeLogin() =
    runTest {
      // Given: Onboarding seen
      hasSeenOnboarding.set(true)

      // Given: Has pin code
      hasPinCode.set(true)

      // Given: Authenticated with DigiD
      isDigidAuthenticated.set(true)

      // Given: Skip pin feature toggle is disabled
      featureToggleRepository.set(flagSkipPinFeatureToggle, false)

      // When: Getting start destination
      val startDestination = viewModel.getStartDestination()

      // Then: Start destination is dashboard
      assertEquals(PinCodeLoginNavigation.Root, startDestination)
    }

  @Test
  fun testStartDestinationDashboard() =
    runTest {
      // Given: Onboarding seen
      hasSeenOnboarding.set(true)

      // Given: Has pin code
      hasPinCode.set(true)

      // Given: Authenticated with DigiD
      isDigidAuthenticated.set(true)

      // Given: Skip pin feature toggle is enabled
      featureToggleRepository.set(flagSkipPinFeatureToggle, true)

      // When: Getting start destination
      val startDestination = viewModel.getStartDestination()

      // Then: Start destination is create pin code
      assertEquals(DashboardNavigation.Root, startDestination)
    }

  @Test
  fun testAppIsLocked() =
    runTest {
      // Given: App is locked
      appLocked.set(true)

      viewModel.navigateDialog.test {
        // When: Calling checkAppLock
        viewModel.checkAppLock()

        // Then: Navigate to login dialog
        assertEquals(PinCodeLoginNavigation.LoginDialog, awaitItem())
      }
    }

  @Test
  fun testAppIsNotLocked() =
    runTest {
      // Given: App is not locked
      appLocked.set(false)

      viewModel.navigateDialog.test {
        // When: Calling checkAppLock
        viewModel.checkAppLock()

        // Then: No navigation is happening
        expectNoEvents()
      }
    }

  @Test
  fun testSaveClosedTimestamp() {
    // When: Calling saveClosedAppTimestamp
    viewModel.saveClosedAppTimestamp()

    // Then: Use case is called
    assertEquals(true, saveClosedAppTimestamp.saved)
  }

  @Test
  fun testGetAutomaticLocalisationEnabled() =
    runTest {
      // Given: automatic localisation is enabled
      keyValueStore.setBoolean(KEY_AUTOMATIC_LOCALISATION, true)

      // When: calling getAutomaticLocalisationEnabled
      val enabled = viewModel.getAutomaticLocalisationEnabled()

      // Then: return true
      assertEquals(true, enabled)
    }
}
