package nl.rijksoverheid.mgo.data.onboarding

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_HAS_SEEN_ONBOARDING
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.runBlocking

internal class DefaultSetHasSeenOnboarding
    @Inject
    constructor(
        @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    ) : SetHasSeenOnboarding {
        override operator fun invoke(hasSeen: Boolean) {
            runBlocking { keyValueStore.setBoolean(KEY_HAS_SEEN_ONBOARDING, hasSeen) }
        }
    }
