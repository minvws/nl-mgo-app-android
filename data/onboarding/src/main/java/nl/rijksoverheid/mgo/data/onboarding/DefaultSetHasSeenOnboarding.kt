package nl.rijksoverheid.mgo.data.onboarding

import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_HAS_SEEN_ONBOARDING
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

/**
 * Set if the onboarding has been seen.
 *
 * @param keyValueStore The [KeyValueStore] to store if the onboarding has been seen.
 */
internal class DefaultSetHasSeenOnboarding
  @Inject
  constructor(
    @Named("keyValueStore") private val keyValueStore: KeyValueStore,
  ) : SetHasSeenOnboarding {
    /**
     * @param hasSeen If the onboarding has been seen
     */
    override operator fun invoke(hasSeen: Boolean) {
      runBlocking { keyValueStore.setBoolean(KEY_HAS_SEEN_ONBOARDING, hasSeen) }
    }
  }
