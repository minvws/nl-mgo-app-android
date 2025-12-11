package nl.rijksoverheid.mgo.feature.pincode.forgot.reset

import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_LOGIN_WITH_BIOMETRIC_ENABLED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_PIN_CODE
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

internal class DefaultResetPinCode
  @Inject
  constructor(
    private val organizationRepository: OrganizationRepository,
    @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    @Named("secureKeyValueStore") private val secureKeyValueStore: KeyValueStore,
  ) : ResetPinCode {
    override suspend fun invoke() {
      // Remove organizations
      organizationRepository.deleteAll()

      // Remove pin code
      secureKeyValueStore.removeString(KEY_PIN_CODE)

      // Remove biometric checked flag
      keyValueStore.removeBoolean(KEY_LOGIN_WITH_BIOMETRIC_ENABLED)
    }
  }
