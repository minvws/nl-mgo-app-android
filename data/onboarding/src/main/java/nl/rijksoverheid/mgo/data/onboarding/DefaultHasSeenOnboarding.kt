package nl.rijksoverheid.mgo.data.onboarding

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_HAS_SEEN_ONBOARDING
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.runBlocking

/**
 * Use case to check if the onboarding has been seen.
 * @param keyValueStore Store to save a key value pair into.
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
