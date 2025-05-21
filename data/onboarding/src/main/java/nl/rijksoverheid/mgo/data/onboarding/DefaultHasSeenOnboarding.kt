package nl.rijksoverheid.mgo.data.onboarding

import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_HAS_SEEN_ONBOARDING
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

/**
 * Check if the onboarding has been seen.
 *
 * @param keyValueStore The [KeyValueStore] to get if the onboarding has been seen.
 */
internal class DefaultHasSeenOnboarding
  @Inject
  constructor(
    @Named("keyValueStore") private val keyValueStore: KeyValueStore,
  ) : HasSeenOnboarding {
    /**
     * @return True if the onboarding has been seen.
     */
    override operator fun invoke(): Boolean {
      return runBlocking { keyValueStore.getBoolean(KEY_HAS_SEEN_ONBOARDING) }
    }
  }
