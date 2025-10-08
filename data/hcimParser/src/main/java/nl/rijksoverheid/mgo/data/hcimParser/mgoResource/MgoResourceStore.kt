package nl.rijksoverheid.mgo.data.hcimParser.mgoResource

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MgoResourceStore
  @Inject
  constructor() {
    private var mgoResources: MutableList<MgoResource> = mutableListOf()

    fun get(referenceId: MgoResourceReferenceId): MgoResource =
      checkNotNull(mgoResources.firstOrNull { mgoResource -> mgoResource.referenceId == referenceId }) {
        "Trying to get mgo resource from store that does not exist"
      }

    fun store(mgoResource: MgoResource) {
      mgoResources.add(mgoResource)
    }

    fun clear() {
      mgoResources.clear()
    }
  }
