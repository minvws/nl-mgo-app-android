package nl.rijksoverheid.mgo.init

import androidx.annotation.VisibleForTesting
import nl.rijksoverheid.mgo.data.digid.SetDigidAuthenticated
import nl.rijksoverheid.mgo.data.hcimParser.javascript.QuickJsRepository
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.onboarding.SetHasSeenOnboarding
import nl.rijksoverheid.mgo.data.pincode.StorePinCode
import nl.rijksoverheid.mgo.framework.featuretoggle.dataSource.FeatureToggleLocalDataSource
import nl.rijksoverheid.mgo.framework.featuretoggle.flagSkipPinFeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import nl.rijksoverheid.mgo.framework.storage.file.CacheFileStore
import javax.inject.Inject

/**
 * This class needs to be called before the app shows any UI since it does a bunch of initialization steps that need to be ready before the app is shown
 * to the user. This is now done in the [nl.rijksoverheid.mgo.MainApplication] class, but preferably should be done in [nl.rijksoverheid.mgo.MainActivity]
 * so it does not block the main thread.
 */
class AppInitializer
  @Inject
  constructor(
    private val featureToggleRepository: FeatureToggleRepository,
    private val featureToggleLocalDataSource: FeatureToggleLocalDataSource,
    private val cacheFileStore: CacheFileStore,
    private val setHasSeenOnboarding: SetHasSeenOnboarding,
    private val storePinCode: StorePinCode,
    private val setDigidAuthenticated: SetDigidAuthenticated,
    private val organizationRepository: OrganizationRepository,
    private val quickJsRepository: QuickJsRepository,
  ) {
    suspend fun init() {
      quickJsRepository.create()
      cacheFileStore.deleteAll()
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
      organizationRepository.deleteAll()
    }
  }
