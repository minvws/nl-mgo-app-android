package nl.rijksoverheid.mgo.data.onboarding

import nl.rijksoverheid.mgo.framework.storage.keyvalue.MgoKeyValueStorage
import javax.inject.Inject
import javax.inject.Named

class SetHasSeenOnboarding
  @Inject
  constructor(
    @Named("sharedPreferencesMgoKeyValueStorage") private val keyValueStorage: MgoKeyValueStorage,
  ) {
    operator fun invoke(hasSeen: Boolean) = keyValueStorage.save(key = KEY_HAS_SEEN_ONBOARDING, value = hasSeen)
  }
