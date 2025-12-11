package nl.rijksoverheid.mgo.init

import androidx.annotation.VisibleForTesting
import nl.rijksoverheid.mgo.data.digid.SetDigidAuthenticated
import nl.rijksoverheid.mgo.data.onboarding.SetHasSeenOnboarding
import nl.rijksoverheid.mgo.data.pincode.StorePinCode
import nl.rijksoverheid.mgo.framework.featuretoggle.dataSource.FeatureToggleLocalDataSource
import nl.rijksoverheid.mgo.framework.featuretoggle.flagSkipPinFeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import nl.rijksoverheid.mgo.reset.ResetApp
import javax.inject.Inject

class AppInitializer
  @Inject
  constructor(
    private val featureToggleRepository: FeatureToggleRepository,
    private val featureToggleLocalDataSource: FeatureToggleLocalDataSource,
    private val setHasSeenOnboarding: SetHasSeenOnboarding,
    private val storePinCode: StorePinCode,
    private val setDigidAuthenticated: SetDigidAuthenticated,
    private val resetApp: ResetApp,
  ) {
    suspend fun init() {
      featureToggleLocalDataSource.init(featureToggleRepository.getAll())
    }

    /**
     * Can be used to set a certain state of the app when launching. Useful for e2e tests.
     */
    @VisibleForTesting
    suspend fun override(
      skipOnboarding: Boolean = false,
      pinCode: List<Int>? = null,
      digidAuthenticated: Boolean = false,
      skipPinCodeLogin: Boolean = false,
    ) {
      resetApp.invoke()
      if (skipOnboarding) {
        setHasSeenOnboarding(true)
      }

      if (pinCode != null) {
        storePinCode(pinCode)
      }

      if (digidAuthenticated) {
        setDigidAuthenticated()
      }

      featureToggleRepository.set(flagSkipPinFeatureToggle, skipPinCodeLogin)
    }

    /**
     * Clear certain state of the app after calling [override]. Useful for e2e tests.
     */
    @VisibleForTesting
    suspend fun clear() {
      resetApp.invoke()
    }
  }
