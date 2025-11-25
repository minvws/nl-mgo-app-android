package nl.rijksoverheid.mgo.reset

import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import nl.rijksoverheid.mgo.framework.storage.keyvalue.MgoKeyValueStorage
import javax.inject.Inject
import javax.inject.Named

class ResetApp
  @Inject
  constructor(
    @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    @Named("secureKeyValueStore") private val secureKeyValueStore: KeyValueStore,
    @Named("sharedPreferencesMgoKeyValueStorage") private val keyValueStorage: MgoKeyValueStorage,
    private val organizationRepository: OrganizationRepository,
  ) {
    suspend operator fun invoke() {
      keyValueStore.clear()
      secureKeyValueStore.clear()
      organizationRepository.deleteAll()
      keyValueStorage.deleteAll()
    }
  }
