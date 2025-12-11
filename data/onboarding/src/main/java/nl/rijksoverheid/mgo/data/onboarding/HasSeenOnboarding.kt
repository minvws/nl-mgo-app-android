package nl.rijksoverheid.mgo.data.onboarding

import nl.rijksoverheid.mgo.framework.storage.keyvalue.MgoKeyValueStorage
import javax.inject.Inject
import javax.inject.Named

internal const val KEY_HAS_SEEN_ONBOARDING = "KEY_HAS_SEEN_ONBOARDING"

class HasSeenOnboarding
  @Inject
  constructor(
    @Named("sharedPreferencesMgoKeyValueStorage") private val keyValueStorage: MgoKeyValueStorage,
  ) {
    operator fun invoke(): Boolean = keyValueStorage.get(KEY_HAS_SEEN_ONBOARDING) ?: false
  }
