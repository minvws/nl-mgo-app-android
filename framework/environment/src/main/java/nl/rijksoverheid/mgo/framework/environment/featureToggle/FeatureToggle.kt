package nl.rijksoverheid.mgo.framework.environment.featureToggle

import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStorageKey

data class FeatureToggle<T>(
  val id: KeyValueStorageKey,
  val name: String,
  val description: String,
  val initialValue: T,
)
