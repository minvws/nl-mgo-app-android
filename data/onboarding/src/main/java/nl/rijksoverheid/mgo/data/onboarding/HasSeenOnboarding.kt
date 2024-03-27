package nl.rijksoverheid.mgo.data.onboarding

import nl.rijksoverheid.mgo.framework.storage.KEY_HAS_SEEN_ONBOARDING
import nl.rijksoverheid.mgo.framework.storage.KeyValueStore
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

class HasSeenOnboarding
    @Inject
    constructor(private val keyValueStore: KeyValueStore) {
        operator fun invoke(): Boolean {
            return runBlocking { keyValueStore.getBoolean(KEY_HAS_SEEN_ONBOARDING) }
        }
    }
