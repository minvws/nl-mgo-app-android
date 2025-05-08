package nl.rijksoverheid.mgo.data.digid

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_DIGID_AUTHENTICATED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

/**
 * Check if the user has authenticated with DigiD.
 *
 * @param keyValueStore The [KeyValueStore] where is stored if has authenticated with DigiD.
 */
internal class DefaultIsDigidAuthenticated
  @Inject
  constructor(
    @Named("keyValueStore") private val keyValueStore: KeyValueStore,
  ) :
  IsDigidAuthenticated {
    /**
     * @return True if the user has authenticated with DigiD.
     */
    override fun invoke(): Boolean {
      return keyValueStore.getBoolean(KEY_DIGID_AUTHENTICATED)
    }
  }
