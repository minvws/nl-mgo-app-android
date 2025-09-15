import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.digid.SetDigidAuthenticated
import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.HealthCareDataStatesRepository
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.onboarding.SetHasSeenOnboarding
import nl.rijksoverheid.mgo.data.pincode.StorePinCode
import nl.rijksoverheid.mgo.framework.featuretoggle.dataSource.FeatureToggleLocalDataSource
import nl.rijksoverheid.mgo.framework.featuretoggle.flagSkipPinFeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import nl.rijksoverheid.mgo.framework.storage.file.CacheFileStore
import nl.rijksoverheid.mgo.init.AppInitializer
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppInitializerTest {
  @get:Rule
  val mockkRule = MockKRule(this)

  @RelaxedMockK
  private lateinit var featureToggleRepository: FeatureToggleRepository

  @RelaxedMockK
  private lateinit var featureToggleLocalDataSource: FeatureToggleLocalDataSource

  @RelaxedMockK
  private lateinit var jsRuntimeRepository: JsRuntimeRepository

  @RelaxedMockK
  private lateinit var cacheFileStore: CacheFileStore

  @RelaxedMockK
  private lateinit var setHasSeenOnboarding: SetHasSeenOnboarding

  @RelaxedMockK
  private lateinit var storePinCode: StorePinCode

  @RelaxedMockK
  private lateinit var setDigidAuthenticated: SetDigidAuthenticated

  @RelaxedMockK
  private lateinit var healthCareDataStatesRepository: HealthCareDataStatesRepository

  @RelaxedMockK
  private lateinit var organizationRepository: OrganizationRepository

  private lateinit var appInitializer: AppInitializer

  @Before
  fun setUp() {
    appInitializer =
      AppInitializer(
        featureToggleRepository,
        featureToggleLocalDataSource,
        jsRuntimeRepository,
        cacheFileStore,
        setHasSeenOnboarding,
        storePinCode,
        setDigidAuthenticated,
        healthCareDataStatesRepository,
        organizationRepository,
      )
  }

  @Test
  fun `init should initialize feature toggles, load JS runtime and clear cache`() =
    runTest {
      val toggles = listOf(flagSkipPinFeatureToggle)
      coEvery { featureToggleRepository.getAll() } returns toggles

      appInitializer.init()

      coVerify { featureToggleLocalDataSource.init(toggles) }
      coVerify { jsRuntimeRepository.load() }
      coVerify { cacheFileStore.deleteAll() }
    }

  @Test
  fun `override should set onboarding when skipOnboarding is true`() =
    runTest {
      appInitializer.override(skipOnboarding = true)

      coVerify { setHasSeenOnboarding(true) }
    }

  @Test
  fun `override should store pinCode when provided`() =
    runTest {
      val pin = listOf(1, 2, 3, 4)

      appInitializer.override(pinCode = pin)

      coVerify { storePinCode(pin) }
    }

  @Test
  fun `override should set digid authentication when digidAuthenticated is true`() =
    runTest {
      appInitializer.override(digidAuthenticated = true)

      coVerify { setDigidAuthenticated() }
    }

  @Test
  fun `override should set skip pin code login toggle`() =
    runTest {
      appInitializer.override(skipPinCodeLogin = true)

      coVerify { featureToggleRepository.set(flagSkipPinFeatureToggle, true) }
    }

  @Test
  fun `clear should delete repositories`() =
    runTest {
      appInitializer.clear()

      coVerify { organizationRepository.deleteAll() }
      coVerify { healthCareDataStatesRepository.deleteAll() }
    }
}
