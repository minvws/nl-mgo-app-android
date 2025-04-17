package nl.rijksoverheid.mgo.data.digid

import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_DIGID_AUTHENTICATED
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

/**
 * Set if the user has authenticated with DigiD.
 *
 * @param keyValueStore The [KeyValueStore] where to store if the user has authenticated with DigiD.
 */
internal class DefaultSetDigidAuthenticated
  @Inject
  constructor(
    @Named("keyValueStore") private val keyValueStore: KeyValueStore,
  ) : SetDigidAuthenticated {
    override operator fun invoke() {
      runBlocking { keyValueStore.setBoolean(KEY_DIGID_AUTHENTICATED, true) }
    }
  }
