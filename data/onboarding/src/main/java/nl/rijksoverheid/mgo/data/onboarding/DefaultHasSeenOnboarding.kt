package nl.rijksoverheid.mgo.data.onboarding

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_HAS_SEEN_ONBOARDING
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.runBlocking

internal class DefaultHasSeenOnboarding
    @Inject
    constructor(
        @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    ) : HasSeenOnboarding {
        override operator fun invoke(): Boolean {
            return runBlocking { keyValueStore.getBoolean(KEY_HAS_SEEN_ONBOARDING) }
        }
    }
