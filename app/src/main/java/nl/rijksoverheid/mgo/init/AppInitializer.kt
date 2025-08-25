package nl.rijksoverheid.mgo.init

import nl.rijksoverheid.mgo.data.digid.SetDigidAuthenticated
import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.onboarding.SetHasSeenOnboarding
import nl.rijksoverheid.mgo.data.pincode.StorePinCode
import nl.rijksoverheid.mgo.framework.featuretoggle.dataSource.FeatureToggleLocalDataSource
import nl.rijksoverheid.mgo.framework.featuretoggle.flagSkipPinFeatureToggle
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import nl.rijksoverheid.mgo.framework.storage.file.CacheFileStore
import javax.inject.Inject

class AppInitializer
  @Inject
  constructor(
    private val featureToggleRepository: FeatureToggleRepository,
    private val featureToggleLocalDataSource: FeatureToggleLocalDataSource,
    private val jsRuntimeRepository: JsRuntimeRepository,
    private val cacheFileStore: CacheFileStore,
    private val setHasSeenOnboarding: SetHasSeenOnboarding,
    private val storePinCode: StorePinCode,
    private val setDigidAuthenticated: SetDigidAuthenticated,
    private val organizationRepository: OrganizationRepository,
  ) {
    suspend fun init() {
      featureToggleLocalDataSource.init(featureToggleRepository.getAll())
      jsRuntimeRepository.load()
      cacheFileStore.deleteAll()
    }

    suspend fun override(
      clearOrganizations: Boolean = true,
      skipOnboarding: Boolean = false,
      pinCode: List<Int>? = null,
      digidAuthenticated: Boolean = false,
      skipPinCodeLogin: Boolean = false,
    ) {
      if (clearOrganizations) {
        organizationRepository.deleteAll()
      }

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
  }
