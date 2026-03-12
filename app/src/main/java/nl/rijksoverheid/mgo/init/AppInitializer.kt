package nl.rijksoverheid.mgo.init

import androidx.annotation.VisibleForTesting
import nl.rijksoverheid.mgo.KEY_SKIP_DIGID_LOGIN
import nl.rijksoverheid.mgo.data.digid.SetDigidAuthenticated
import nl.rijksoverheid.mgo.data.onboarding.SetHasSeenOnboarding
import nl.rijksoverheid.mgo.framework.featuretoggle.dataSource.FeatureToggleLocalDataSource
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import nl.rijksoverheid.mgo.framework.storage.keyvalue.MgoKeyValueStorage
import nl.rijksoverheid.mgo.reset.ResetApp
import javax.inject.Inject
import javax.inject.Named

class AppInitializer
  @Inject
  constructor(
    private val featureToggleRepository: FeatureToggleRepository,
    private val featureToggleLocalDataSource: FeatureToggleLocalDataSource,
    private val setHasSeenOnboarding: SetHasSeenOnboarding,
    private val setDigidAuthenticated: SetDigidAuthenticated,
    private val resetApp: ResetApp,
    @Named("sharedPreferencesMgoKeyValueStorage") private val keyValueStorage: MgoKeyValueStorage,
  ) {
    suspend fun init() {
      featureToggleLocalDataSource.init(featureToggleRepository.getAll())
    }

    /**
     * Can be used to set a certain state of the app when launching. Useful for e2e tests.
     */
    @VisibleForTesting
    fun override(
      skipOnboarding: Boolean = false,
      digidAuthenticated: Boolean = false,
      skipDigidLogin: Boolean = false,
    ) {
      resetApp.invoke()
      if (skipOnboarding) {
        setHasSeenOnboarding(true)
      }

      if (digidAuthenticated) {
        setDigidAuthenticated()
      }

      if (skipDigidLogin) {
        keyValueStorage.save(KEY_SKIP_DIGID_LOGIN, true)
      }
    }

    /**
     * Clear certain state of the app after calling [override]. Useful for e2e tests.
     */
    @VisibleForTesting
    fun clear() {
      resetApp.invoke()
    }
  }
